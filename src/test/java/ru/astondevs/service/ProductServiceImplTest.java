package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.repository.impl.ProductRepositoryImpl;
import ru.astondevs.service.impl.ProductServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.astondevs.TestUtils.PRODUCT;
import static ru.astondevs.TestUtils.PRODUCT_DTO;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepositoryImpl repository;

    private ProductService service;

    @BeforeEach
    void init() {
        service = new ProductServiceImpl(repository);
    }

    @Test
    void testAdd() {
        when(repository.add(PRODUCT)).thenReturn(PRODUCT);
        assertEquals(PRODUCT_DTO, service.add(PRODUCT_DTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(PRODUCT));
        assertEquals(List.of(PRODUCT_DTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(PRODUCT);
        assertEquals(PRODUCT_DTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(PRODUCT_DTO);
        verify(repository).update(PRODUCT);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
