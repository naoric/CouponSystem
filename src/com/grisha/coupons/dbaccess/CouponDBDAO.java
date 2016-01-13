package com.grisha.coupons.dbaccess;

import com.grisha.coupons.beans.Coupon;
import com.grisha.coupons.beans.CouponType;
import com.grisha.coupons.dao.CouponDAO;
import com.mysql.jdbc.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by naoric on 1/13/2016.
 */
public class CouponDBDAO implements CouponDAO {

    private ConnectionPool connectionPool;

    public CouponDBDAO() throws SQLException, ClassNotFoundException {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public Coupon createCoupon(Coupon coupon) throws SQLException {
        Connection connection = connectionPool.getConnection();
        String sql = "INSERT INTO COUPONS (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, coupon.getTitle());
        preparedStatement.setDate(2, new Date(coupon.getStartDate().getTime()));
        preparedStatement.setDate(3, new Date(coupon.getEndDate().getTime()));
        preparedStatement.setInt(4, coupon.getAmount());
        preparedStatement.setString(5, coupon.getType().toString());
        preparedStatement.setString(6, coupon.getMessage());
        preparedStatement.setDouble(7, coupon.getPrice());
        preparedStatement.setString(8, coupon.getImage());


        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            long couponId = generatedKeys.getLong(1);
            coupon.setId(couponId);
        }

        connectionPool.returnConnection(connection);

        return coupon;
    }

    @Override
    public Coupon removeCoupon(Coupon coupon) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "DELETE FROM COUPONS WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, coupon.getId());

        preparedStatement.executeUpdate();

        connectionPool.returnConnection(connection);

        return coupon;
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "UPDATE COUPONS SET AMOUNT = ?, START_DATE = ?, END_DATE = ?, IMAGE = ?, PRICE = ?, TITLE = ?, TYPE = ?, MESSAGE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, coupon.getAmount());
        preparedStatement.setDate(2, new Date(coupon.getStartDate().getTime()));
        preparedStatement.setDate(3, new Date(coupon.getEndDate().getTime()));
        preparedStatement.setString(4, coupon.getImage());
        preparedStatement.setDouble(5, coupon.getPrice());
        preparedStatement.setString(6, coupon.getTitle());
        preparedStatement.setString(7, coupon.getType().toString());
        preparedStatement.setString(8, coupon.getMessage());

        preparedStatement.executeUpdate();

        connectionPool.returnConnection(connection);
        return coupon;
    }

    @Override
    public Coupon getCoupon(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "SELECT * FROM COUPONS WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setAmount(resultSet.getInt("AMOUNT"));
        coupon.setStartDate(resultSet.getDate("START_DATE"));
        // TODO FILL IN OTHER FIELDS

        connectionPool.returnConnection(connection);
        return coupon;
    }

    @Override
    public Collection<Coupon> getAllCoupons() throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        Connection connection = connectionPool.getConnection();
        String sql = "SELECT * FROM COUPONS";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Coupon coupon = new Coupon();

            coupon.setId(resultSet.getInt("ID"));
            coupon.setAmount(resultSet.getInt("AMOUNT"));
            // TODO all the rest

            coupons.add(coupon);
        }

        connectionPool.returnConnection(connection);
        return coupons;
    }

    @Override
    public Collection<Coupon> getCouponsByType(CouponType couponType) {
        return null;
    }
}
