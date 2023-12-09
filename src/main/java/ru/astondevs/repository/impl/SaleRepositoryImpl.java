package ru.astondevs.repository.impl;

import ru.astondevs.entity.Sale;
import ru.astondevs.util.DbConnector;
import ru.astondevs.repository.SaleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class SaleRepositoryImpl implements SaleRepository {
    private final DbConnector connector;

    public SaleRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public long add(Sale sale) {
        String SQL = "INSERT INTO sale(date, seller, product, count, price, sum) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        long id = 0;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(sale.getDate()));
            pstmt.setLong(2, sale.getSeller());
            pstmt.setLong(3, sale.getProduct());
            pstmt.setInt(4, sale.getCount());
            pstmt.setDouble(5, sale.getPrice());
            pstmt.setDouble(6, sale.getSum());

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
    public Sale findById(long id) {
        Sale sale = null;
        String SQL = "SELECT * FROM sale WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sale = resultSetToSale(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return sale;
    }

    @Override
    public List<Sale> findAll() {
        List<Sale> list = new ArrayList<>();
        String SQL = "SELECT * FROM sale";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToSale(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public List<Sale> findSalesBySeller(long sellerId) {
        List<Sale> list = new ArrayList<>();
        String SQL = "SELECT * FROM sale WHERE seller = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToSale(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void update(Sale sale) throws SQLException {
        String SQL = "UPDATE sale " +
                "SET date = ?, seller = ?, product = ?, count = ?, price = ?, sum = ? " +
                "WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(sale.getDate()));
            pstmt.setLong(2, sale.getSeller());
            pstmt.setLong(3, sale.getProduct());
            pstmt.setInt(4, sale.getCount());
            pstmt.setDouble(5, sale.getPrice());
            pstmt.setDouble(6, sale.getSum());
            pstmt.setLong(7, sale.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String SQL = "DELETE FROM sale WHERE id = ?";

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private Sale resultSetToSale(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getLong("id"));
        sale.setDate(rs.getTimestamp("date").toLocalDateTime());
        sale.setSeller(rs.getLong("seller"));
        sale.setProduct(rs.getLong("product"));
        sale.setCount(rs.getInt("count"));
        sale.setPrice(rs.getDouble("price"));
        sale.setSum(rs.getDouble("sum"));
        return sale;
    }
}
