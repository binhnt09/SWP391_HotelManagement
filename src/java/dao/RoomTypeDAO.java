/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Amenity;
import entity.AmenityCategory;
import entity.RoomType;
import entity.Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addServicesToRoomType(int roomTypeId, List<Integer> serviceIds) {
        String sql = "INSERT INTO RoomType_Service (RoomTypeID, ServiceID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int serviceId : serviceIds) {
                ps.setInt(1, roomTypeId);
                ps.setInt(2, serviceId);
                ps.addBatch();  // Tối ưu bằng cách dùng batch insert
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

    public void deleteServicesByRoomType(int roomTypeId) {
        String sql = "DELETE FROM RoomType_Service WHERE RoomTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isRoomTypeNameExists(String name, int excludeId) {
        String query = "SELECT COUNT(*) FROM RoomType WHERE typeName = ? AND roomtypeid <> ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public List<Service> getServicesByRoomTypeId(int roomTypeId) {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.ServiceID, s.Name, s.Description, s.Price "
                + "FROM RoomType_Service rts "
                + "JOIN Service s ON rts.ServiceID = s.ServiceID "
                + "WHERE rts.RoomTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Service s = new Service();
                    s.setServiceId(rs.getInt("ServiceID"));
                    s.setName(rs.getString("Name"));
                    s.setDescription(rs.getString("Description"));
                    s.setPrice(rs.getBigDecimal("Price"));
                    services.add(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return services;
    }

    public boolean deleteRoomType(int roomTypeID) {
        String sql = "DELETE FROM RoomTypeAmenity WHERE roomTypeID = ?; "
                + "DELETE FROM RoomType_Service WHERE roomTypeID = ? ;"
                + "   DELETE FROM RoomType WHERE RoomTypeID = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeID);
            ps.setInt(2, roomTypeID);
            ps.setInt(3, roomTypeID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new dao.RoomTypeDAO().deleteRoomType(36));
    }

}
