package ru.astondevs.repository;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepository<T> {
    T add(T product);

    T findById(long id);

    List<T> findAll();

    void update(T product) throws SQLException;

    void delete(long id) throws SQLException;
}
