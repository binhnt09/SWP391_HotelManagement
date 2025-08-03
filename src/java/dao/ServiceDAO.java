/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author viet7
 */
public class ServiceDAO extends DBContext {

    public List<Service> searchServicesByPage(String keyword, int offset, int limit) {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM Service "
                + "WHERE IsDeleted = 0 AND (Name LIKE ? OR Category LIKE ? OR Description LIKE ?) "
                + "ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setInt(4, offset);
            ps.setInt(5, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractService(rs));
            }

        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int getTotalServiceSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM Service WHERE IsDeleted = 0 AND "
                + "(Name LIKE ? OR Category LIKE ? OR Description LIKE ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public boolean insertService(Service s) {
        String sql = "INSERT INTO Service (Name, ImageURL, Description, Price, Category, Status, CreatedAt, UpdatedAt, IsDeleted) "
                + "VALUES (?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getImageUrl());
            ps.setString(3, s.getDescription());
            ps.setBigDecimal(4, s.getPrice());
            ps.setString(5, s.getCategory());
            ps.setBoolean(6, s.isStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updateService(Service s) {
        String sql = "UPDATE Service SET Name=?, ImageURL=?, Description=?, Price=?, Category=?, Status=?, "
                + "UpdatedAt=GETDATE() WHERE ServiceID=? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getImageUrl());
            ps.setString(3, s.getDescription());
            ps.setBigDecimal(4, s.getPrice());
            ps.setString(5, s.getCategory());
            ps.setBoolean(6, s.isStatus());
            ps.setInt(7, s.getServiceId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean deleteService(int id, int deletedBy) {
        String sql = "UPDATE Service SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE ServiceID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, deletedBy);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public Service getServiceById(int id) {
        String sql = "SELECT * FROM Service WHERE ServiceID = ? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractService(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean isServiceInUse(int serviceId) {
        String sql = "SELECT COUNT(*) FROM BookingService WHERE ServiceID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public List<Service> searchServicesByFilter(String keyword, String category, Boolean status,
            String sortField, String sortOrder,
            int offset, int limit) {
        List<Service> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM Service WHERE IsDeleted = 0");

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (Name LIKE ? OR Description LIKE ?)");
        }

        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category = ?");
        }

        if (status != null) {
            sql.append(" AND Status = ?");
        }

        if (sortField != null && !sortField.isEmpty()) {
            if (sortField.equals("Name") || sortField.equals("Price")) {
                sql.append(" ORDER BY ").append(sortField);
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }
        } else {
            sql.append(" ORDER BY CreatedAt DESC");
        }

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
                ps.setString(paramIndex++, "%" + keyword + "%");
            }

            if (category != null && !category.isEmpty()) {
                ps.setString(paramIndex++, category);
            }

            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractService(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public int countServicesByFilter(String keyword, String category, Boolean status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Service WHERE IsDeleted = 0");

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (Name LIKE ? OR Description LIKE ?)");
        }

        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category = ?");
        }

        if (status != null) {
            sql.append(" AND Status = ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
                ps.setString(paramIndex++, "%" + keyword + "%");
            }

            if (category != null && !category.isEmpty()) {
                ps.setString(paramIndex++, category);
            }

            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    private Service extractService(ResultSet rs) throws SQLException {
        return new Service(
                rs.getInt("ServiceID"),
                rs.getString("Name"),
                rs.getString("ImageURL"),
                rs.getString("Description"),
                rs.getBigDecimal("Price"),
                rs.getString("Category"),
                rs.getBoolean("Status"),
                rs.getTimestamp("CreatedAt"),
                rs.getTimestamp("UpdatedAt"),
                rs.getTimestamp("DeletedAt"),
                rs.getObject("DeletedBy") != null ? rs.getInt("DeletedBy") : null,
                rs.getBoolean("IsDeleted")
        );
    }

    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM Service WHERE IsDeleted = 0 AND Status = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractService(rs));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return list;
    }

    public List<Service> getListService() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM Service WHERE IsDeleted = 0 AND Status = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractService(rs));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return list;
    }

    public List<Service> getAvailableServicesNotInRoomType(int roomTypeId) {
        List<Service> list = new ArrayList<>();
        String sql = """
        SELECT * FROM Service 
        WHERE IsDeleted = 0 AND Status = 1 
        AND ServiceID NOT IN (
            SELECT ServiceID FROM RoomType_Service WHERE RoomTypeID = ?
        )
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractService(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ServiceDAO serviceDAO = new ServiceDAO(); // hoặc truyền connection nếu cần
        int roomTypeId = 1; // thay bằng RoomTypeID bạn muốn test

        List<Service> services = serviceDAO.getAvailableServicesNotInRoomType(roomTypeId);

        for (Service s : services) {
            System.out.println("Service ID: " + s.getServiceId());
            System.out.println("Name: " + s.getServiceId());
            System.out.println("Price: " + s.getPrice());
            System.out.println("Description: " + s.getDescription());
            System.out.println("--------------------------");
        }
    }
}
