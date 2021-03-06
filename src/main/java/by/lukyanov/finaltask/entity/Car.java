package by.lukyanov.finaltask.entity;

import java.math.BigDecimal;
import java.util.Optional;

public final class Car extends AbstractEntity{
    private String brand;
    private String model;
    private String vinCode;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private boolean active = true;
    private String image;
    private CarCategory carCategory;
    private CarInfo info;

    public Car() {
        super();
    }

    public Car(long id){
        super(id);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Optional<BigDecimal> getSalePrice() {
        return Optional.ofNullable(salePrice);
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public CarInfo getInfo() {
        return info;
    }

    public void setInfo(CarInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Car car = (Car) o;

        if (active != car.active) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (vinCode != null ? !vinCode.equals(car.vinCode) : car.vinCode != null) return false;
        if (regularPrice != null ? !regularPrice.equals(car.regularPrice) : car.regularPrice != null) return false;
        if (salePrice != null ? !salePrice.equals(car.salePrice) : car.salePrice != null) return false;
        if (image != null ? !image.equals(car.image) : car.image != null) return false;
        if (carCategory != null ? !carCategory.equals(car.carCategory) : car.carCategory != null) return false;
        return info != null ? info.equals(car.info) : car.info == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (vinCode != null ? vinCode.hashCode() : 0);
        result = 31 * result + (regularPrice != null ? regularPrice.hashCode() : 0);
        result = 31 * result + (salePrice != null ? salePrice.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (carCategory != null ? carCategory.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", vinCode='").append(vinCode).append('\'');
        sb.append(", regularPrice=").append(regularPrice);
        sb.append(", salePrice=").append(salePrice);
        sb.append(", active=").append(active);
        sb.append(", image='").append(image).append('\'');
        sb.append(", carCategory=").append(carCategory);
        sb.append(", info=").append(info);
        sb.append('}');
        return sb.toString();
    }

    public static class CarBuilder{
        private final Car car;

        public CarBuilder() {
            car = new Car();
        }

        public CarBuilder id(Long id){
            car.setId(id);
            return this;
        }

        public CarBuilder brand(String brand){
            car.brand = brand;
            return this;
        }

        public CarBuilder model(String model){
            car.model = model;
            return this;
        }

        public CarBuilder vin(String vinCode){
            car.vinCode = vinCode;
            return this;
        }

        public CarBuilder regularPrice(BigDecimal regularPrice){
            car.regularPrice = regularPrice;
            return this;
        }

        public CarBuilder salePrice(BigDecimal salePrice){
            car.salePrice = salePrice;
            return this;
        }

        public CarBuilder active(boolean active){
            car.active = active;
            return this;
        }

        public CarBuilder image(String image){
            car.image = image;
            return this;
        }

        public CarBuilder category(CarCategory category){
            car.carCategory = category;
            return this;
        }

        public CarBuilder carInfo(CarInfo info){
            car.info = info;
            return this;
        }

        public Car build(){
            return car;
        }
    }
}
