package ru.astondevs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static ru.astondevs.TestUtils.PRODUCT;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {
    @Mock
    private DbConnector dbConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private ProductRepositoryImpl productRepository;

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
        when(resultSet.getLong(1)).thenReturn(PRODUCT.getId());

        assertEquals(PRODUCT, productRepository.add(PRODUCT));
    }

    @Test
    void testFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(PRODUCT.getId());
        when(resultSet.getString("name")).thenReturn(PRODUCT.getName());
        when(resultSet.getBigDecimal("price")).thenReturn(PRODUCT.getPrice());

        assertEquals(List.of(PRODUCT), productRepository.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(PRODUCT.getId());
        when(resultSet.getString("name")).thenReturn(PRODUCT.getName());
        when(resultSet.getBigDecimal("price")).thenReturn(PRODUCT.getPrice());

        assertEquals(PRODUCT, productRepository.findById(1L));
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        productRepository.update(PRODUCT);

        verify(statement).setString(1, PRODUCT.getName());
        verify(statement).setBigDecimal(2, PRODUCT.getPrice());
        verify(statement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        productRepository.delete(PRODUCT.getId());

        verify(statement).setLong(1, PRODUCT.getId());
        verify(statement).executeUpdate();
    }
}
