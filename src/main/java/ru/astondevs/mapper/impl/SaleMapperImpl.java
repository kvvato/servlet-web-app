package ru.astondevs.mapper.impl;

import ru.astondevs.dto.SaleDto;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.SaleMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaleMapperImpl implements SaleMapper {
    @Override
    public SaleDto toDto(Sale sale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = sale.getDate().format(formatter);

        return SaleDto.builder()
                .id(sale.getId())
                .date(dateString)
                .seller(sale.getSeller())
                .product(sale.getProduct())
                .count(sale.getCount())
                .price(sale.getPrice())
                .sum(sale.getSum())
                .build();
    }

    @Override
    public Sale fromDto(SaleDto saleDto) {
        LocalDateTime localDateTime = LocalDateTime.parse(saleDto.getDate());

        return Sale.builder()
                .id(saleDto.getId())
                .date(localDateTime)
                .seller(saleDto.getSeller())
                .product(saleDto.getProduct())
                .count(saleDto.getCount())
                .price(saleDto.getPrice())
                .sum(saleDto.getSum())
                .build();
    }
}
