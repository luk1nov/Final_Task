package by.lukyanov.finaltask.model.dao;

import by.lukyanov.finaltask.entity.CarCategory;
import by.lukyanov.finaltask.exception.DaoException;

import java.util.Optional;

public interface CarCategoryDao extends BaseDao<CarCategory> {
    Optional<CarCategory> findById(String id) throws DaoException;
}