package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.service.impl.SaleServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.astondevs.TestUtils.SALE;
import static ru.astondevs.TestUtils.SALE_DTO;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {
    @Mock
    private SaleRepositoryImpl repository;

    private SaleService service;

    @BeforeEach
    void init() {
        service = new SaleServiceImpl(repository);
    }

    @Test
    void testAdd() {
        when(repository.add(SALE)).thenReturn(SALE);
        assertEquals(SALE_DTO, service.add(SALE_DTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(SALE));
        assertEquals(List.of(SALE_DTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(SALE);
        assertEquals(SALE_DTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(SALE_DTO);
        verify(repository).update(SALE);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
