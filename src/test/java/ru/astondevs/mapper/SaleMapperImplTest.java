package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.SaleDTO;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.impl.SaleMapperImpl;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleMapperImplTest {
    private final Sale sale;
    private final SaleDTO saleDTO;
    private final SaleMapper saleMapper = new SaleMapperImpl();

    public SaleMapperImplTest() {
        LocalDateTime date = LocalDateTime.of(2023, Month.DECEMBER, 9, 16, 0);
        String stringDate = "2023-12-09T16:00:00";
        sale = new Sale(1L, date, 1L, 1L, 2, 20.0, 40.0);
        saleDTO = new SaleDTO(1L, stringDate, 1L, 1L, 2, 20.0, 40.0);
    }

    @Test
    public void testToDto() {
        SaleDTO actual = saleMapper.toDto(sale);
        assertEquals(saleDTO, actual);
    }

    @Test
    public void testToEntity() {
        Sale actual = saleMapper.toEntity(saleDTO);
        assertEquals(sale, actual);
    }
}
