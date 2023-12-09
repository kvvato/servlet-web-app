package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.dto.SaleDTO;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.SaleMapper;
import ru.astondevs.mapper.impl.SaleMapperImpl;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.service.impl.SaleServiceImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {
    @Mock
    private SaleRepositoryImpl repository;

    private final SaleMapper mapper = new SaleMapperImpl();
    private final Sale sale;
    private final SaleDTO saleDTO;
    private SaleService service;

    public SaleServiceImplTest() {
        LocalDateTime date = LocalDateTime.of(2023, Month.DECEMBER, 9, 16, 0);
        String stringDate = "2023-12-09T16:00:00";
        sale = new Sale(1L, date, 1L, 1L, 2, 20.0, 40.0);
        saleDTO = new SaleDTO(1L, stringDate, 1L, 1L, 2, 20.0, 40.0);
    }

    @BeforeEach
    void init() {
        service = new SaleServiceImpl(repository, mapper);
    }

    @Test
    void testAdd() {
        when(repository.add(sale)).thenReturn(1L);
        assertEquals(saleDTO, service.add(saleDTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(sale));
        assertEquals(List.of(saleDTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(sale);
        assertEquals(saleDTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(saleDTO);
        verify(repository).update(sale);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
