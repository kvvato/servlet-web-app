package ru.astondevs.mapper;

import ru.astondevs.dto.SaleDTO;
import ru.astondevs.entity.Sale;

public interface SaleMapper {
    SaleDTO toDto(Sale sale);

    Sale toEntity(SaleDTO saleDto);
}
