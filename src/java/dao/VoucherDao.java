/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class VoucherDao extends DBContext {

    public Voucher getByVoucherId(int voucherId) {
        String sql = "SELECT VoucherID, Code, DiscountPercentage, ValidFrom, ValidTo, "
                + " CreatedAt, UpdatedAt "
                + " FROM Voucher WHERE VoucherID = ? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Voucher v = new Voucher();
                v.setVoucherId(rs.getInt("VoucherID"));
                v.setCode(rs.getString("Code"));
                v.setDiscountPercentage(rs.getDouble("DiscountPercentage"));
                v.setValidFrom(rs.getTimestamp("ValidFrom"));
                v.setValidTo(rs.getTimestamp("ValidTo"));
                v.setCreatedAt(rs.getTimestamp("CreatedAt"));
                v.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                
                return v;
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
