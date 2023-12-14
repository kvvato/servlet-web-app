package ru.astondevs.mapper.impl;

import ru.astondevs.dto.ProductDto;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    @Override
    public Product fromDto(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
    }
}
