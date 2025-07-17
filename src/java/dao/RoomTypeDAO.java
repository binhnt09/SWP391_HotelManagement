/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Amenity;
import entity.AmenityCategory;
import entity.RoomType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class RoomTypeDAO extends DBContext {

    public RoomType getRoomTypeById(int id) {
        String sql = "select * from roomtype";
        if (id != -1) {
            sql += "   where RoomTypeID = ?";
        }
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            if (id != -1) {
                pre.setInt(1, id);
            }
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new RoomType(rs.getInt("RoomTypeID"),
                        rs.getString("TypeName"),
                        rs.getString("description"),
                        rs.getString("imageurl"),
                        rs.getInt("NumberPeople"),
                        rs.getString("Amenity"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),
                        rs.getBoolean("IsDeleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RoomType> getListRoomType() {
        List<RoomType> list = new ArrayList<>();
        String sql = "select * from RoomType";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new RoomType(rs.getInt("RoomTypeID"),
                        rs.getString("TypeName"),
                        rs.getString("description"),
                        rs.getString("imageurl"),
                        rs.getInt("numberPeople"),
                        rs.getString("amenity"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),
                        rs.getBoolean("IsDeleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateRoomType(RoomType type) {
        String sql = "UPDATE RoomType SET TypeName = ?, description = ?, imageurl = ?, numberPeople = ?, amenity = ?, UpdatedAt = GETDATE() WHERE RoomTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, type.getTypeName());
            ps.setString(2, type.getDescription());
            ps.setString(3, type.getImageUrl());
            ps.setInt(4, type.getNumberPeople());
            ps.setString(5, type.getAmenity());
            ps.setInt(6, type.getRoomTypeID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Amenity> getAmenitiesByRoomTypeId(int roomTypeId) {
        List<Amenity> list = new ArrayList<>();
        String sql = "SELECT a.AmenityID, a.Name, c.CategoryID, c.CategoryName "
                + "FROM RoomTypeAmenity rta "
                + "JOIN Amenity a ON rta.AmenityID = a.AmenityID "
                + "JOIN AmenityCategory c ON a.CategoryID = c.CategoryID "
                + "WHERE rta.RoomTypeID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AmenityCategory category = new AmenityCategory();
                category.setCategoryId(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));

                Amenity a = new Amenity();
                a.setAmenityId(rs.getInt("AmenityID"));
                a.setName(rs.getString("Name"));
                a.setCategory(category);

                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addAmenitiesToRoomType(int roomTypeId, List<Integer> amenityIds) {
        String sql = "INSERT INTO RoomTypeAmenity (RoomTypeID, AmenityID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int amenityId : amenityIds) {
                ps.setInt(1, roomTypeId);
                ps.setInt(2, amenityId);
                ps.addBatch();  // dùng batch để tối ưu
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addNewRoomType(String typeName, int numberPeople, String amenity, String description) {
        String sql = "insert into RoomType (TypeName,NumberPeople,amenity,description,ImageURL,createdAt) values (?,?,?,?,?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, typeName);
            ps.setInt(2, numberPeople);
            ps.setString(3, amenity);
            ps.setString(4, description);
            ps.setString(5, "");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getLatestRoomTypeId() {
        String sql = "SELECT MAX(RoomTypeID) AS MaxID FROM RoomType";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MaxID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    
    public void deleteAmenitiesByRoomType(int roomTypeId) {
        String sql = "DELETE FROM RoomTypeAmenity WHERE RoomTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Amenity>> getAmenitiesGroupedByCategoryByRoomType(int roomTypeId) {
        String sql = "SELECT ac.CategoryID, ac.CategoryName, a.AmenityID, a.Name "
                + "FROM RoomTypeAmenity rta "
                + "JOIN Amenity a ON rta.AmenityID = a.AmenityID "
                + "JOIN AmenityCategory ac ON a.CategoryID = ac.CategoryID "
                + "WHERE rta.RoomTypeID = ? "
                + "ORDER BY ac.CategoryID, a.Name";

        Map<String, List<Amenity>> grouped = new LinkedHashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String categoryName = rs.getString("CategoryName");

                Amenity a = new Amenity();
                a.setAmenityId(rs.getInt("AmenityID"));
                a.setName(rs.getString("Name"));

                AmenityCategory category = new AmenityCategory();
                category.setCategoryId(rs.getInt("CategoryID"));
                category.setCategoryName(categoryName);
                a.setCategory(category);

                grouped.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grouped;
    }

    public Map<String, List<Amenity>> getAmenitiesGroupedByCategory() {
        Map<String, List<Amenity>> result = new LinkedHashMap<>();
        String sql = "SELECT a.AmenityID, a.Name, ac.CategoryName "
                + "FROM Amenity a JOIN AmenityCategory ac ON a.CategoryID = ac.CategoryID "
                + "ORDER BY ac.CategoryName, a.Name";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("AmenityID");
                String name = rs.getString("Name");
                String categoryName = rs.getString("CategoryName");

                Amenity amenity = new Amenity();
                amenity.setAmenityId(id);
                amenity.setName(name);

                // Nhóm theo CategoryName
                result.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(amenity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
