package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.dto.SellerDTO;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.SellerMapper;
import ru.astondevs.mapper.impl.SellerMapperImpl;
import ru.astondevs.repository.impl.SellerRepositoryImpl;
import ru.astondevs.service.impl.SellerServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {
    @Mock
    private SellerRepositoryImpl repository;

    private final SellerMapper mapper = new SellerMapperImpl();
    private final Seller seller = new Seller(1L, "Иван");
    private final SellerDTO sellerDTO = new SellerDTO(1L, "Иван");
    private SellerService service;

    @BeforeEach
    void init() {
        service = new SellerServiceImpl(repository, mapper);
    }

    @Test
    void testAdd() {
        when(repository.add(seller)).thenReturn(1L);
        assertEquals(sellerDTO, service.add(sellerDTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(seller));
        assertEquals(List.of(sellerDTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(seller);
        assertEquals(sellerDTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(sellerDTO);
        verify(repository).update(seller);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
