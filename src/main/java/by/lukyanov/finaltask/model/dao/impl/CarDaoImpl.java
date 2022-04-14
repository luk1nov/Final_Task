package by.lukyanov.finaltask.model.dao.impl;

import by.lukyanov.finaltask.entity.Car;
import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.entity.CarInfo;
import by.lukyanov.finaltask.exception.DaoException;
import by.lukyanov.finaltask.model.connection.ConnectionPool;
import by.lukyanov.finaltask.model.dao.CarDao;
import by.lukyanov.finaltask.util.ImageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CARS = "SELECT cars.car_id, cars.brand, cars.model, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain, car_category.car_category_title FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id INNER JOIN car_category on cars.car_category_id = car_category.car_category_id ORDER BY cars.car_id";
    private static final String SQL_FIND_CAR_BY_ID = "SELECT cars.car_id, cars.brand, cars.model, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id WHERE car_id = ?";
    private static final String SQL_INSERT_NEW_CAR = "INSERT INTO cars (brand,model,regular_price,sale_price,is_active) values(?,?,?,?,?)";
    private static final String SQL_INSERT_NEW_CAR_WITH_IMAGE = "INSERT INTO cars (brand,model,regular_price,sale_price,is_active, image) values(?,?,?,?,?,?)";
    private static final String SQL_INSERT_NEW_CAR_INFO = "INSERT INTO car_info (acceleration,power,drivetrain,cars_car_id) values(?,?,?,?)";
    private static final String SQL_UPDATE_CAR_BY_ID = "UPDATE cars SET brand = ?, model = ?, regular_price = ?, sale_price = ?, is_active = ?, car_category_id = ? WHERE car_id = ?";
    private static final String SQL_UPDATE_CAR_WITH_IMAGE_BY_ID = "UPDATE cars SET brand = ?, model = ?, regular_price = ?, sale_price = ?, is_active = ?, car_category_id = ?, image = ? WHERE car_id = ?";
    private static final String SQL_UPDATE_CAR_INFO_BY_CAR_ID = "UPDATE car_info SET acceleration = ?, power = ?, drivetrain = ? WHERE cars_car_id = ?";
    private static final String SQL_FIND_CARS_BY_CATEGORY_ID = "SELECT cars.car_id, cars.brand, cars.model, cars.regular_price, cars.sale_price, cars.is_active, cars.image, car_info.acceleration, car_info.power, car_info.drivetrain FROM cars LEFT JOIN car_info ON cars.car_id = car_info.cars_car_id WHERE car_category_id = ?";
    private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars WHERE car_id = ?";
    private static final String SQL_UPDATE_STATUS_CAR_BY_ID = "UPDATE cars SET is_active = ? WHERE car_id = ?";
    private static CarDaoImpl instance;
    private final ConnectionPool pool = ConnectionPool.getInstance();


    private CarDaoImpl() {
    }

    public static CarDaoImpl getInstance(){
        if (instance == null){
            instance = new CarDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(Car car) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement addCarStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement addCarInfoStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR_INFO)) {
                connection.setAutoCommit(false); // 1
                addCarStatement.setString(1, car.getBrand());
                addCarStatement.setString(2, car.getModel());
                addCarStatement.setBigDecimal(3, car.getRegularPrice());
                addCarStatement.setBigDecimal(4, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                addCarStatement.setBoolean(5, car.isActive());
                addCarStatement.executeUpdate(); // 2
                try (ResultSet resultSetCarId = addCarStatement.getGeneratedKeys()){
                    if (resultSetCarId.next() ) {
                        int carId = resultSetCarId.getInt(1);
                        addCarInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                        addCarInfoStatement.setInt(2, car.getInfo().getPower());
                        addCarInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                        addCarInfoStatement.setLong(4,carId);
                        addCarInfoStatement.executeUpdate();
                        result = true;
                        connection.commit();
                        logger.info("successful query - commit");
                    }
                }
            } catch (SQLException e) {
                logger.error("SQL exception trying add new car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true); // 4
            }
        } catch (SQLException e){
            logger.error("Dao exception trying add new car", e);
            throw new DaoException(e);
        }
        return result;
    }

    public boolean delete(String id) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CAR_BY_ID)) {
            statement.setString(1, id);
            if (statement.executeUpdate() != 0){
                result = true;
                logger.info("user deleted " + id);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying delete car", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Car> findAll() throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CARS);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                Car car = new Car.CarBuilder()
                        .id(Long.parseLong(resultSet.getString(1)))
                        .brand(resultSet.getString(2))
                        .model(resultSet.getString(3))
                        .regularPrice(resultSet.getBigDecimal(4))
                        .salePrice(resultSet.getString(5) != null ? resultSet.getBigDecimal(5) : null)
                        .active(resultSet.getBoolean(6))
                        .image(ImageEncoder.getInstance().encodeBlob(resultSet.getBlob(7)))
                        .carInfo(new CarInfo(resultSet.getDouble(8), resultSet.getInt(9),
                                CarInfo.Drivetrain.valueOf(resultSet.getString(10).toUpperCase())))
                        .category(new CarCategory(resultSet.getString(11)))
                        .build();
                cars.add(car);
            }
        } catch (SQLException | IOException e) {
            logger.error("Dao exception trying find all cars", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public boolean update(Car car) throws DaoException {
        boolean updated;
        logger.debug(car.toString());
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement carStatement = connection.prepareStatement(SQL_UPDATE_CAR_BY_ID);
            PreparedStatement carInfoStatement = connection.prepareStatement(SQL_UPDATE_CAR_INFO_BY_CAR_ID)){
                connection.setAutoCommit(false);
                carStatement.setString(1, car.getBrand());
                carStatement.setString(2, car.getModel());
                carStatement.setBigDecimal(3, car.getRegularPrice());
                carStatement.setBigDecimal(4, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                carStatement.setBoolean(5, car.isActive());
                carStatement.setLong(6, car.getCarCategory().getId());
                logger.debug(car.getCarCategory().getId());
                carStatement.setLong(7, car.getId());
                carStatement.executeUpdate();
                carInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                carInfoStatement.setInt(2, car.getInfo().getPower());
                carInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                carInfoStatement.setLong(4, car.getId());
                carInfoStatement.executeUpdate();
                updated = true;
                connection.commit();
            } catch (SQLException e){
                logger.error("SQL exception trying update car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying edit car", e);
            throw new DaoException(e);
        }
        return updated;
    }

    @Override
    public Optional<Car> findCarById(String id) throws DaoException {
        Optional<Car> foundCar;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CAR_BY_ID)){
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    Car car = new Car.CarBuilder()
                            .id(Long.parseLong(resultSet.getString(1)))
                            .brand(resultSet.getString(2))
                            .model(resultSet.getString(3))
                            .regularPrice(resultSet.getBigDecimal(4))
                            .salePrice(resultSet.getString(5) != null ? resultSet.getBigDecimal(5) : null)
                            .active(resultSet.getBoolean(6))
                            .image(ImageEncoder.getInstance().encodeBlob(resultSet.getBlob(7)))
                            .carInfo(new CarInfo(resultSet.getDouble(8), resultSet.getInt(9),
                                    CarInfo.Drivetrain.valueOf(resultSet.getString(10).toUpperCase())))
                            .build();
                    foundCar = Optional.of(car);
                } else {
                    foundCar = Optional.empty();
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Dao exception trying find car by id", e);
            throw new DaoException(e);
        }
        return foundCar;
    }

    @Override
    public boolean insertWithImage(Car car, InputStream carImage) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement addCarStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR_WITH_IMAGE, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement addCarInfoStatement = connection.prepareStatement(SQL_INSERT_NEW_CAR_INFO)) {
                connection.setAutoCommit(false); // 1
                addCarStatement.setString(1, car.getBrand());
                addCarStatement.setString(2, car.getModel());
                addCarStatement.setBigDecimal(3, car.getRegularPrice());
                addCarStatement.setBigDecimal(4, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                addCarStatement.setBoolean(5, car.isActive());
                addCarStatement.setBlob(6, carImage);
                addCarStatement.executeUpdate(); // 2
                try (ResultSet resultSetCarId = addCarStatement.getGeneratedKeys()){
                    if (resultSetCarId.next() ) {
                        int carId = resultSetCarId.getInt(1);
                        addCarInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                        addCarInfoStatement.setInt(2, car.getInfo().getPower());
                        addCarInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                        addCarInfoStatement.setLong(4, carId);
                        addCarInfoStatement.executeUpdate();
                        result = true;
                        connection.commit();
                        logger.info("successful query - commit");
                    }
                }
            } catch (SQLException e) {
                logger.error("SQL exception trying add new car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true); // 4
            }
        } catch (SQLException e){
            logger.error("Dao exception trying add new car", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean updateWithImage(Car car, InputStream carImage) throws DaoException {
        boolean updated;
        try (Connection connection = pool.getConnection()){
            try (PreparedStatement carStatement = connection.prepareStatement(SQL_UPDATE_CAR_WITH_IMAGE_BY_ID);
                 PreparedStatement carInfoStatement = connection.prepareStatement(SQL_UPDATE_CAR_INFO_BY_CAR_ID)){
                connection.setAutoCommit(false);
                carStatement.setString(1, car.getBrand());
                carStatement.setString(2, car.getModel());
                carStatement.setBigDecimal(3, car.getRegularPrice());
                carStatement.setBigDecimal(4, car.getSalePrice().isPresent() ? car.getSalePrice().get() : null);
                carStatement.setBoolean(5, car.isActive());
                carStatement.setLong(6, car.getCarCategory().getId());
                carStatement.setBlob(7, carImage.available() != 0 ? carImage : null);
                carStatement.setLong(8, car.getId());
                carStatement.executeUpdate();
                carInfoStatement.setDouble(1, car.getInfo().getAcceleration());
                carInfoStatement.setInt(2, car.getInfo().getPower());
                carInfoStatement.setString(3, car.getInfo().getDrivetrain().toString());
                carInfoStatement.setLong(4, car.getId());
                carInfoStatement.executeUpdate();
                updated = true;
                connection.commit();
            } catch (SQLException | IOException e){
                logger.error("Dao exception trying update car - rollback", e);
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying edit car", e);
            throw new DaoException(e);
        }
        return updated;
    }

    @Override
    public List<Car> findCarsByCategoryId(String id) throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CARS_BY_CATEGORY_ID)){
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Car car = new Car.CarBuilder()
                            .id(Long.parseLong(resultSet.getString(1)))
                            .brand(resultSet.getString(2))
                            .model(resultSet.getString(3))
                            .regularPrice(resultSet.getBigDecimal(4))
                            .salePrice(resultSet.getString(5) != null ? resultSet.getBigDecimal(5) : null)
                            .active(resultSet.getBoolean(6))
                            .image(ImageEncoder.getInstance().encodeBlob(resultSet.getBlob(7)))
                            .carInfo(new CarInfo(resultSet.getDouble(8), resultSet.getInt(9),
                                    CarInfo.Drivetrain.valueOf(resultSet.getString(10).toUpperCase())))
                            .build();
                    cars.add(car);
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Dao exception trying find all cars", e);
            throw new DaoException(e);
        }
        return cars;
    }

    @Override
    public boolean changeCarActiveById(String id, boolean isActive) throws DaoException {
        boolean result = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS_CAR_BY_ID)){
            statement.setBoolean(1, isActive);
            statement.setString(2, id);
            if (statement.executeUpdate() != 0){
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Dao exception trying change car active status", e);
            throw new DaoException(e);
        }
        return result;
    }
}
