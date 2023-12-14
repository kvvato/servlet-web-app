package ru.astondevs.repository;

import ru.astondevs.entity.Sale;

import java.util.List;

public interface SaleRepository extends BaseRepository<Sale> {
    List<Sale> findSalesBySellerId(long sellerId);
}
