package ru.astondevs.repository;

import ru.astondevs.entity.Sale;

import java.sql.SQLException;
import java.util.List;

public interface SaleRepository {
    long add(Sale sale);

    Sale findById(long id);

    List<Sale> findAll();

    List<Sale> findSalesBySeller(long sellerId);

    void update(Sale sale) throws SQLException;

    void delete(long id) throws SQLException;
}
