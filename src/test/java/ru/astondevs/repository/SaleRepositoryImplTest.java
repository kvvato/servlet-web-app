package ru.astondevs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.entity.Sale;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaleRepositoryImplTest {
    @Mock
    private DbConnector dbConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private SaleRepositoryImpl saleRepository;
    private final LocalDateTime date = LocalDateTime.of(2023, Month.DECEMBER, 9, 16, 0);
    private final Sale sale = new Sale(1L, date, 1L, 1L, 2, 20.0, 40.0);

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
        when(resultSet.getLong(1)).thenReturn(1L);

        assertEquals(sale.getId(), saleRepository.add(sale));
    }

    @Test
    void testFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getTimestamp("date")).thenReturn(Timestamp.valueOf(date));
        when(resultSet.getLong("seller")).thenReturn(1L);
        when(resultSet.getLong("product")).thenReturn(1L);
        when(resultSet.getInt("count")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(20.0);
        when(resultSet.getDouble("sum")).thenReturn(40.0);

        assertEquals(List.of(sale), saleRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getTimestamp("date")).thenReturn(Timestamp.valueOf(date));
        when(resultSet.getLong("seller")).thenReturn(1L);
        when(resultSet.getLong("product")).thenReturn(1L);
        when(resultSet.getInt("count")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(20.0);
        when(resultSet.getDouble("sum")).thenReturn(40.0);

        assertEquals(sale, saleRepository.findById(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        saleRepository.update(sale);

        verify(statement).setTimestamp(1, Timestamp.valueOf(date));
        verify(statement).setLong(2, 1L);
        verify(statement).setLong(3, 1L);
        verify(statement).setInt(4, 2);
        verify(statement).setDouble(5, 20.0);
        verify(statement).setDouble(6, 40.0);
        verify(statement).setLong(7, 1L);
        verify(statement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        saleRepository.delete(1L);

        verify(statement).setLong(1, 1L);
        verify(statement).executeUpdate();
    }
}
