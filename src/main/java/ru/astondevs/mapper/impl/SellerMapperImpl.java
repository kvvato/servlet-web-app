package ru.astondevs.mapper.impl;

import ru.astondevs.dto.SellerDTO;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.SellerMapper;

public class SellerMapperImpl implements SellerMapper {
    @Override
    public SellerDTO toDto(Seller seller) {
        return new SellerDTO(seller.getId(), seller.getName());
    }

    @Override
    public Seller toEntity(SellerDTO sellerDto) {
        return new Seller(sellerDto.getId(), sellerDto.getName());
    }
}
