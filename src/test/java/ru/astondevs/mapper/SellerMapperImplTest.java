package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.SellerDTO;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.impl.SellerMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerMapperImplTest {
    private final Seller seller = new Seller(1L, "Иван");
    private final SellerDTO sellerDTO = new SellerDTO(1L, "Иван");
    private final SellerMapper sellerMapper = new SellerMapperImpl();

    @Test
    public void testToDto() {
        SellerDTO actual = sellerMapper.toDto(seller);
        assertEquals(sellerDTO, actual);
    }

    @Test
    public void testToEntity() {
        Seller actual = sellerMapper.toEntity(sellerDTO);
        assertEquals(seller, actual);
    }
}
