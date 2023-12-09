package ru.astondevs.repository.impl;

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


public class ProductRepositoryImpl implements ProductRepository {
    private final DbConnector connector;

    public ProductRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public long add(Product product) {
        String SQL = "INSERT INTO product(name, price) "
                + "VALUES(?, ?)";

        long id = 0;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String SQL = "SELECT * FROM product";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToProduct(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Product findById(long id) {
        Product product = null;
        String SQL = "SELECT * FROM product WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = resultSetToProduct(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return product;
    }

    @Override
    public List<Product> findSoldProductsBySeller(long sellerId) {
        List<Product> list = new ArrayList<>();
        String SQL = "SELECT product.* FROM sale " +
                "LEFT JOIN product ON sale.product = product.id " +
                "WHERE seller = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToProduct(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void update(Product product) throws SQLException {
        String SQL = "UPDATE product " +
                "SET name = ?, price = ? " +
                "WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setLong(3, product.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String SQL = "DELETE FROM product "
                + "WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private Product resultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        return product;
    }
}
