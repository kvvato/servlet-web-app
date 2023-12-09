package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.dto.ProductDTO;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.ProductMapper;
import ru.astondevs.mapper.impl.ProductMapperImpl;
import ru.astondevs.repository.impl.ProductRepositoryImpl;
import ru.astondevs.service.impl.ProductServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepositoryImpl repository;

    private final ProductMapper mapper = new ProductMapperImpl();
    private final Product product = new Product(1L, "Ручка", 20.0);
    private final ProductDTO productDTO = new ProductDTO(1L, "Ручка", 20.0);
    private ProductService service;

    @BeforeEach
    void init() {
        service = new ProductServiceImpl(repository, mapper);
    }

    @Test
    void testAdd() {
        when(repository.add(product)).thenReturn(1L);
        assertEquals(productDTO, service.add(productDTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(product));
        assertEquals(List.of(productDTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(product);
        assertEquals(productDTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(productDTO);
        verify(repository).update(product);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
