package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.SellerDto;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.impl.SellerMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.astondevs.TestUtils.SELLER;
import static ru.astondevs.TestUtils.SELLER_DTO;

class SellerMapperImplTest {
    private final SellerMapper sellerMapper = new SellerMapperImpl();

    @Test
    public void testToDto() {
        SellerDto actual = sellerMapper.toDto(SELLER);
        assertEquals(SELLER_DTO, actual);
    }

    @Test
    public void testToEntity() {
        Seller actual = sellerMapper.fromDto(SELLER_DTO);
        assertEquals(SELLER, actual);
    }
}
