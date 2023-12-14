package ru.astondevs.mapper.impl;

import ru.astondevs.dto.SellerDto;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.SellerMapper;

public class SellerMapperImpl implements SellerMapper {
    @Override
    public SellerDto toDto(Seller seller) {
        return SellerDto.builder()
                .id(seller.getId())
                .name(seller.getName())
                .build();
    }

    @Override
    public Seller fromDto(SellerDto sellerDto) {
        return Seller.builder()
                .id(sellerDto.getId())
                .name(sellerDto.getName())
                .build();
    }
}
