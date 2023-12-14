package ru.astondevs.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static ru.astondevs.repository.RepositoryUtils.ID_COLUMN;


public class SaleRepositoryImpl implements SaleRepository {
    private static final String ADD_QUERY = "INSERT INTO sale(date, seller, product, count, price, sum) "
            + "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM sale";
    private static final String FIND_BY_ID_QUERY = String.format("SELECT * FROM sale WHERE %s = ?", ID_COLUMN);
    private static final String FIND_SALES_BY_SELLER_ID_QUERY = "SELECT * FROM sale WHERE seller = ?";
    private static final String UPDATE_QUERY = String.format("UPDATE sale " +
            "SET date = ?, seller = ?, product = ?, count = ?, price = ?, sum = ? " +
            "WHERE %s = ?", ID_COLUMN);
    private static final String DELETE_QUERY = String.format("DELETE FROM sale WHERE %s = ?", ID_COLUMN);

    private final DbConnector connector;
    private final Logger logger = LoggerFactory.getLogger(SaleRepositoryImpl.class);

    public SaleRepositoryImpl() {
        connector = new DbConnector();
    }

    public SaleRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public Sale add(Sale sale) {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            setQueryParams(pstmt, sale);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        sale.setId(id);
                        return sale;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Sale creation error", ex);
        }
        return null;
    }

    @Override
    public Sale findById(long id) {
        Sale sale = null;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sale = resultSetToSale(rs);
            }
        } catch (SQLException ex) {
            logger.error("Sale request error", ex);
        }

        return sale;
    }

    @Override
    public List<Sale> findAll() {
        List<Sale> list = new ArrayList<>();

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToSale(rs));
            }
        } catch (SQLException ex) {
            logger.error("All sales request error", ex);
        }

        return list;
    }

    @Override
    public List<Sale> findSalesBySellerId(long sellerId) {
        List<Sale> list = new ArrayList<>();

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_SALES_BY_SELLER_ID_QUERY)) {

            pstmt.setLong(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToSale(rs));
            }
        } catch (SQLException ex) {
            logger.error("Sales by seller request error", ex);
        }

        return list;
    }

    @Override
    public void update(Sale sale) throws SQLException {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {

            setQueryParams(pstmt, sale);
            pstmt.setLong(7, sale.getId());
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

    private void setQueryParams(PreparedStatement pstmt, Sale sale) throws SQLException {
        pstmt.setTimestamp(1, Timestamp.valueOf(sale.getDate()));
        pstmt.setLong(2, sale.getSeller());
        pstmt.setLong(3, sale.getProduct());
        pstmt.setInt(4, sale.getCount());
        pstmt.setBigDecimal(5, sale.getPrice());
        pstmt.setBigDecimal(6, sale.getSum());
    }

    private Sale resultSetToSale(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getLong(ID_COLUMN));
        sale.setDate(rs.getTimestamp("date").toLocalDateTime());
        sale.setSeller(rs.getLong("seller"));
        sale.setProduct(rs.getLong("product"));
        sale.setCount(rs.getInt("count"));
        sale.setPrice(rs.getBigDecimal("price"));
        sale.setSum(rs.getBigDecimal("sum"));
        return sale;
    }
}
