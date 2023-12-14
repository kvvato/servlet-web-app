package ru.astondevs.mapper;

import ru.astondevs.dto.SaleDto;
import ru.astondevs.entity.Sale;

public interface SaleMapper {
    SaleDto toDto(Sale sale);

    Sale fromDto(SaleDto saleDto);
}
