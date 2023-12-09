package ru.astondevs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.entity.Seller;
import ru.astondevs.repository.impl.SellerRepositoryImpl;
import ru.astondevs.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerRepositoryImplTest {
    @Mock
    private DbConnector dbConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private SellerRepositoryImpl sellerRepository;
    private final Seller seller = new Seller(1L, "Иван");

    @BeforeEach
    void init() {
        when(dbConnector.connect()).thenReturn(connection);

        sellerRepository = new SellerRepositoryImpl(dbConnector);
    }

    @Test
    void testAdd() throws SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(1L);

        assertEquals(seller.getId(), sellerRepository.add(seller));
    }

    @Test
    void testFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Иван");

        assertEquals(List.of(seller), sellerRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Иван");

        assertEquals(seller, sellerRepository.findById(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        sellerRepository.update(seller);

        verify(statement).setString(1, "Иван");
        verify(statement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        sellerRepository.delete(1L);

        verify(statement).setLong(1, 1L);
        verify(statement).executeUpdate();
    }
}
