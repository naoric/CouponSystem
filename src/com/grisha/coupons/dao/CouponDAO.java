package com.grisha.coupons.dao;

import com.grisha.coupons.beans.Coupon;
import com.grisha.coupons.beans.CouponType;

import java.sql.SQLException;
import java.util.Collection;

public interface CouponDAO {
    public Coupon createCoupon(Coupon coupon) throws SQLException;

    public Coupon removeCoupon(Coupon coupon) throws SQLException;

    public Coupon updateCoupon(Coupon coupon) throws SQLException;

    public Coupon getCoupon(int id) throws SQLException;

    public Collection<Coupon> getAllCoupons() throws SQLException;

    public Collection<Coupon> getCouponsByType(CouponType couponType);

}