package ru.astondevs.repository;

import ru.astondevs.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    long add(Product product);

    Product findById(long id);

    List<Product> findAll();

    List<Product> findSoldProductsBySeller(long sellerId);

    void update(Product product) throws SQLException;

    void delete(long id) throws SQLException;
}
