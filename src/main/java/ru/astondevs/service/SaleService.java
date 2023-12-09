package ru.astondevs.service;

import ru.astondevs.dto.SaleDTO;

import java.sql.SQLException;
import java.util.List;

public interface SaleService {
    SaleDTO add(SaleDTO product);

    SaleDTO get(long id);

    List<SaleDTO> getAll();

    List<SaleDTO> getBySeller(long sellerId);

    void update(SaleDTO cashier) throws SQLException;

    void remove(long id) throws SQLException;
}
