package ru.astondevs.mapper;

import ru.astondevs.dto.ProductDto;
import ru.astondevs.entity.Product;

public interface ProductMapper {
    ProductDto toDto(Product product);

    Product fromDto(ProductDto productDto);
}
