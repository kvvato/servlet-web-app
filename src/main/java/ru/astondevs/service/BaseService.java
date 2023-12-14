package ru.astondevs.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {
    T add(T product);

    T get(long id);

    List<T> getAll();

    void update(T product) throws SQLException;

    void remove(long id) throws SQLException;
}
