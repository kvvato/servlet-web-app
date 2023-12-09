package ru.astondevs.repository;

import ru.astondevs.entity.Seller;

import java.sql.SQLException;
import java.util.List;

public interface SellerRepository {
    long add(Seller seller);

    Seller findById(long id);

    List<Seller> findAll();

    void update(Seller product) throws SQLException;

    void delete(long id) throws SQLException;
}
