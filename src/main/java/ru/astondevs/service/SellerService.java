package ru.astondevs.service;

import ru.astondevs.dto.SellerDTO;

import java.sql.SQLException;
import java.util.List;

public interface SellerService {
    SellerDTO add(SellerDTO cashier);

    SellerDTO get(long id);

    List<SellerDTO> getAll();

    void update(SellerDTO cashier) throws SQLException;

    void remove(long id) throws SQLException;
}
