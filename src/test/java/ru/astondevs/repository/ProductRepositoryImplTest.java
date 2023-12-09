package ru.astondevs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.entity.Product;
import ru.astondevs.repository.impl.ProductRepositoryImpl;
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
public class ProductRepositoryImplTest {
    @Mock
    private DbConnector dbConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private ProductRepositoryImpl productRepository;
    private final Product product = new Product(1L, "Ручка", 20.0);

    @BeforeEach
    void init() {
        when(dbConnector.connect()).thenReturn(connection);

        productRepository = new ProductRepositoryImpl(dbConnector);
    }

    @Test
    void testAdd() throws SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(1L);

        assertEquals(product.getId(), productRepository.add(product));
    }

    @Test
    void testFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ручка");
        when(resultSet.getDouble("price")).thenReturn(20.0);

        assertEquals(List.of(product), productRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Ручка");
        when(resultSet.getDouble("price")).thenReturn(20.0);

        assertEquals(product, productRepository.findById(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        productRepository.update(product);

        verify(statement).setString(1, "Ручка");
        verify(statement).setDouble(2, 20.0);
        verify(statement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        productRepository.delete(1L);

        verify(statement).setLong(1, 1L);
        verify(statement).executeUpdate();
    }
}
