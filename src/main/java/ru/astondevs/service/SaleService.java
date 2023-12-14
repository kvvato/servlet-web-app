package ru.astondevs.service;

import ru.astondevs.dto.SaleDto;

import java.util.List;

public interface SaleService extends BaseService<SaleDto> {
    List<SaleDto> getBySellerId(long sellerId);
}
