package ru.astondevs.service;

import ru.astondevs.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    ProductDTO add(ProductDTO product);

    ProductDTO get(long id);

    List<ProductDTO> getAll();

    List<ProductDTO> getSoldProductsBySeller(long sellerId);

    void update(ProductDTO product) throws SQLException;

    void remove(long id) throws SQLException;
}
