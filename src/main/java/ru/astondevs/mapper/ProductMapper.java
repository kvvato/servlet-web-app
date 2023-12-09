package ru.astondevs.mapper;

import ru.astondevs.dto.ProductDTO;
import ru.astondevs.entity.Product;

public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDto);
}
