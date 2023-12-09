package ru.astondevs.mapper.impl;

import ru.astondevs.dto.ProductDTO;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDTO toDto(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    @Override
    public Product toEntity(ProductDTO productDto){
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }
}
