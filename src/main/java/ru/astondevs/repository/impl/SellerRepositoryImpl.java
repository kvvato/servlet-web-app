package ru.astondevs.repository.impl;

import ru.astondevs.entity.Seller;
import ru.astondevs.util.DbConnector;
import ru.astondevs.repository.SellerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SellerRepositoryImpl implements SellerRepository {
    private final DbConnector connector;

    public SellerRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public long add(Seller seller) {
        String SQL = "INSERT INTO seller(name) VALUES(?)";

        long id = 0;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, seller.getName());

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
    public Seller findById(long id) {
        Seller seller = null;
        String SQL = "SELECT * FROM seller WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                seller = resultSetToCashier(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return seller;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> list = new ArrayList<>();
        String SQL = "SELECT * FROM seller";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToCashier(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void update(Seller product) throws SQLException {
        String SQL = "UPDATE seller SET name = ? WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String SQL = "DELETE FROM seller WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private Seller resultSetToCashier(ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getLong("id"));
        seller.setName(rs.getString("name"));
        return seller;
    }
}
