package ru.astondevs.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.astondevs.entity.Product;
import ru.astondevs.util.DbConnector;
import ru.astondevs.repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.astondevs.repository.RepositoryUtils.ID_COLUMN;


public class ProductRepositoryImpl implements ProductRepository {
    private static final String ADD_QUERY = "INSERT INTO product(name, price) VALUES(?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM product";
    private static final String FIND_BY_ID_QUERY = String.format("SELECT * FROM product WHERE %s = ?", ID_COLUMN);
    private static final String FIND_SOLD_PRODUCTS_BY_SELLER_ID_QUERY = String.format("SELECT product.* FROM sale " +
            "LEFT JOIN product ON sale.product = product.%s " +
            "WHERE sale.seller = ?", ID_COLUMN);
    private static final String UPDATE_QUERY = String.format("UPDATE product SET name = ?, price = ? WHERE %s = ?", ID_COLUMN);
    private static final String DELETE_QUERY = String.format("DELETE FROM product WHERE %s = ?", ID_COLUMN);

    private final DbConnector connector;
    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    public ProductRepositoryImpl() {
        connector = new DbConnector();
    }

    public ProductRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public Product add(Product product) {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        product.setId(id);
                        return product;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Product creation error", ex);
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToProduct(rs));
            }
        } catch (SQLException ex) {
            logger.error("All products request error", ex);
        }

        return list;
    }

    @Override
    public Product findById(long id) {
        Product product = null;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = resultSetToProduct(rs);
            }
        } catch (SQLException ex) {
            logger.error("Product request error", ex);
        }

        return product;
    }

    @Override
    public List<Product> findSoldProductsBySellerId(long sellerId) {
        List<Product> list = new ArrayList<>();

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_SOLD_PRODUCTS_BY_SELLER_ID_QUERY)) {

            pstmt.setLong(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToProduct(rs));
            }
        } catch (SQLException ex) {
            logger.error("Sold products by seller request error", ex);
        }

        return list;
    }

    @Override
    public void update(Product product) throws SQLException {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setLong(3, product.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private Product resultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong(ID_COLUMN));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        return product;
    }
}
