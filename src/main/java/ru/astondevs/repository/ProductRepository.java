package ru.astondevs.repository;

import ru.astondevs.entity.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> findSoldProductsBySellerId(long sellerId);
}
