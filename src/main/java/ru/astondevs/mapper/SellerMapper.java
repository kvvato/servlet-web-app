package ru.astondevs.mapper;

import ru.astondevs.dto.SellerDto;
import ru.astondevs.entity.Seller;

public interface SellerMapper {
    SellerDto toDto(Seller seller);

    Seller fromDto(SellerDto sellerDto);
}
