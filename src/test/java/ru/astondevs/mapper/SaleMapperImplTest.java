package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.SaleDto;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.impl.SaleMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.astondevs.TestUtils.SALE;
import static ru.astondevs.TestUtils.SALE_DTO;

class SaleMapperImplTest {
    private final SaleMapper saleMapper = new SaleMapperImpl();

    @Test
    public void testToDto() {
        SaleDto actual = saleMapper.toDto(SALE);
        assertEquals(SALE_DTO, actual);
    }

    @Test
    public void testToEntity() {
        Sale actual = saleMapper.fromDto(SALE_DTO);
        assertEquals(SALE, actual);
    }
}
