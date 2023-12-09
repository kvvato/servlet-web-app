package ru.astondevs.mapper.impl;

import ru.astondevs.dto.SaleDTO;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.SaleMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaleMapperImpl implements SaleMapper {
    @Override
    public SaleDTO toDto(Sale sale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = sale.getDate().format(formatter);
        return new SaleDTO(sale.getId(), dateString, sale.getSeller(), sale.getProduct(),
                sale.getCount(), sale.getPrice(), sale.getSum());
    }

    @Override
    public Sale toEntity(SaleDTO saleDto) {
        LocalDateTime localDateTime = LocalDateTime.parse(saleDto.getDate());
        return new Sale(saleDto.getId(), localDateTime, saleDto.getSeller(), saleDto.getProduct(),
                saleDto.getCount(), saleDto.getPrice(), saleDto.getSum());
    }
}
