/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.MembershipLevel;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class MembershipDao extends DBContext {

    public List<MembershipLevel> getUnassignedLevelsByVoucherId(int voucherId) {
        List<MembershipLevel> levels = new ArrayList<>();

        String sql = """
            SELECT LevelID, LevelName
            FROM MembershipLevel
            WHERE LevelID NOT IN (
                SELECT LevelID
                FROM VoucherLevel
                WHERE VoucherID = ? AND IsDeleted = 0
            )
            AND IsDeleted = 0
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, voucherId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MembershipLevel level = new MembershipLevel();
                    level.setLevelId(rs.getInt("LevelID"));
                    level.setLevelName(rs.getString("LevelName"));
                    levels.add(level);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return levels;
    }

    public List<Integer> getSelectedMembershipLevels(int voucherId) {
        List<Integer> levelIds = new ArrayList<>();
        String sql = "SELECT LevelID FROM VoucherLevel WHERE VoucherID = ? AND IsDeleted = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                levelIds.add(rs.getInt("LevelID"));
            }
        } catch (Exception e) {
            Logger.getLogger(MembershipDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return levelIds;
    }
}
