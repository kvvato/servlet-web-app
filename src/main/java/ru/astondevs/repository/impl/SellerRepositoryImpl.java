package ru.astondevs.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static ru.astondevs.repository.RepositoryUtils.ID_COLUMN;


public class SellerRepositoryImpl implements SellerRepository {
    private static final String ADD_QUERY = "INSERT INTO seller(name) VALUES(?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM seller";
    private static final String FIND_BY_ID_QUERY = String.format("SELECT * FROM seller WHERE %s = ?", ID_COLUMN);
    private static final String UPDATE_QUERY = String.format("UPDATE seller SET name = ? WHERE %s = ?", ID_COLUMN);
    private static final String DELETE_QUERY = String.format("DELETE FROM seller WHERE %s = ?", ID_COLUMN);

    private final DbConnector connector;
    private final Logger logger = LoggerFactory.getLogger(SellerRepositoryImpl.class);

    public SellerRepositoryImpl() {
        connector = new DbConnector();
    }

    public SellerRepositoryImpl(DbConnector connector) {
        this.connector = connector;
    }

    @Override
    public Seller add(Seller seller) {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, seller.getName());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        seller.setId(id);
                        return seller;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Seller creation error", ex);
        }
        return null;
    }

    @Override
    public Seller findById(long id) {
        Seller seller = null;

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                seller = resultSetToCashier(rs);
            }
        } catch (SQLException ex) {
            logger.error("Seller request error", ex);
        }

        return seller;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> list = new ArrayList<>();

        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(resultSetToCashier(rs));
            }
        } catch (SQLException ex) {
            logger.error("All sellers request error", ex);
        }

        return list;
    }

    @Override
    public void update(Seller product) throws SQLException {
        try (Connection conn = connector.connect();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getId());
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

    private Seller resultSetToCashier(ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getLong(ID_COLUMN));
        seller.setName(rs.getString("name"));
        return seller;
    }
}
