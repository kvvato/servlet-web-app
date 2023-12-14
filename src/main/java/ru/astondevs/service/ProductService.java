package ru.astondevs.service;

import ru.astondevs.dto.ProductDto;

import java.util.List;

public interface ProductService extends BaseService<ProductDto> {
    List<ProductDto> getSoldProductsBySellerId(long sellerId);
}
