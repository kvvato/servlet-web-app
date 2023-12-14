package ru.astondevs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.repository.impl.SellerRepositoryImpl;
import ru.astondevs.service.impl.SellerServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.astondevs.TestUtils.SELLER;
import static ru.astondevs.TestUtils.SELLER_DTO;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    @Mock
    private SellerRepositoryImpl repository;

    private SellerService service;

    @BeforeEach
    void init() {
        service = new SellerServiceImpl(repository);
    }

    @Test
    void testAdd() {
        when(repository.add(SELLER)).thenReturn(SELLER);
        assertEquals(SELLER_DTO, service.add(SELLER_DTO));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(SELLER));
        assertEquals(List.of(SELLER_DTO), service.getAll());
    }

    @Test
    void testGetById() {
        when(repository.findById(anyLong())).thenReturn(SELLER);
        assertEquals(SELLER_DTO, service.get(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        service.update(SELLER_DTO);
        verify(repository).update(SELLER);
    }

    @Test
    void testDelete() throws SQLException {
        service.remove(1L);
        verify(repository).delete(anyLong());
    }
}
