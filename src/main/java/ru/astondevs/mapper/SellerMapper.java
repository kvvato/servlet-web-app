package ru.astondevs.mapper;

import ru.astondevs.dto.SellerDTO;
import ru.astondevs.entity.Seller;

public interface SellerMapper {
    SellerDTO toDto(Seller seller);

    Seller toEntity(SellerDTO sellerDto);
}
