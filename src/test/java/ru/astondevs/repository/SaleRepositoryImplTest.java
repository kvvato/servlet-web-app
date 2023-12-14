package ru.astondevs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.astondevs.TestUtils.SALE;
import static ru.astondevs.TestUtils.SALE_DATE;

@ExtendWith(MockitoExtension.class)
class SaleRepositoryImplTest {
    @Mock
    private DbConnector dbConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private SaleRepositoryImpl saleRepository;

    @BeforeEach
    void init() {
        when(dbConnector.connect()).thenReturn(connection);

        saleRepository = new SaleRepositoryImpl(dbConnector);
    }

    @Test
    void testAdd() throws SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(SALE.getId());

        assertEquals(SALE, saleRepository.add(SALE));
    }

    @Test
    void testFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(SALE.getId());
        when(resultSet.getTimestamp("date")).thenReturn(Timestamp.valueOf(SALE.getDate()));
        when(resultSet.getLong("seller")).thenReturn(SALE.getSeller());
        when(resultSet.getLong("product")).thenReturn(SALE.getProduct());
        when(resultSet.getInt("count")).thenReturn(SALE.getCount());
        when(resultSet.getBigDecimal("price")).thenReturn(SALE.getPrice());
        when(resultSet.getBigDecimal("sum")).thenReturn(SALE.getSum());

        assertEquals(List.of(SALE), saleRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(SALE.getId());
        when(resultSet.getTimestamp("date")).thenReturn(Timestamp.valueOf(SALE.getDate()));
        when(resultSet.getLong("seller")).thenReturn(SALE.getSeller());
        when(resultSet.getLong("product")).thenReturn(SALE.getProduct());
        when(resultSet.getInt("count")).thenReturn(SALE.getCount());
        when(resultSet.getBigDecimal("price")).thenReturn(SALE.getPrice());
        when(resultSet.getBigDecimal("sum")).thenReturn(SALE.getSum());

        assertEquals(SALE, saleRepository.findById(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        saleRepository.update(SALE);

        verify(statement).setTimestamp(1, Timestamp.valueOf(SALE.getDate()));
        verify(statement).setLong(2, SALE.getSeller());
        verify(statement).setLong(3, SALE.getProduct());
        verify(statement).setInt(4, SALE.getCount());
        verify(statement).setBigDecimal(5, SALE.getPrice());
        verify(statement).setBigDecimal(6, SALE.getSum());
        verify(statement).setLong(7, SALE.getId());
        verify(statement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        saleRepository.delete(SALE.getId());

        verify(statement).setLong(1, SALE.getId());
        verify(statement).executeUpdate();
    }
}
