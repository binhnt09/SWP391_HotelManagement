/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Hotel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Room;
import entity.RoomDetail;
import entity.RoomType;
import java.sql.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RoomDAO extends DBContext {

    public List<Room> getListRoom(Date checkin, Date checkout,
            double from, double to,
            int numberPeople, int roomTypeId,
            String keyword, String status,
            String sortBy, boolean isAsc,
            int pageIndex, int pageSize,
            boolean isDeleted
    ) {
        List<Room> list = new ArrayList();
        String sql = "select r.RoomID , r.RoomNumber , r.RoomDetailID, r.RoomTypeID , r.Status , r.Price, r.HotelID , r.CreatedAt , r.UpdatedAt,r.DeletedAt , r.DeletedBy,r.IsDeleted\n"
                + "from Room r\n"
                + "inner join roomtype rt on rt.RoomTypeID = r.RoomTypeID\n"
                + "inner join RoomDetail rd on rd.RoomDetailID = r.RoomDetailID\n"
                + "where r.IsDeleted = ? \n";
        if (status != null && !status.equalsIgnoreCase("all")) {
            sql += "AND r.Status = ? ";
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "AND ( r.RoomID LIKE ? or r.RoomNumber LIKE ? OR rd.BedType LIKE ? OR rd.Description LIKE ?) ";
        }

        if (roomTypeId != -1) {
            sql += " and rt.RoomTypeID = ? ";
        }
        if (from != -1.0 || to != -1.0) {
            if (from != -1 && to != -1) {
                sql += " and (? <= r.Price and r.Price <= ?)";
            } else if (from == -1.0) {
                sql += " and r.price <= ?";
            } else if (to == -1) {
                sql += " and r.price >= ?";
            }
        }
        if (numberPeople != -1) {
            sql += " and rd.MaxGuest >= ?";
        }

        if (checkin != null || checkout != null) {
            sql += "	and r.RoomID not in(\n"
                    + "		select bd.RoomID from BookingDetail bd\n"
                    + "		inner join Booking b on b.BookingID = bd.BookingID "
                    + "   WHERE 1=1 ";
            if (checkin != null && checkout != null) {
                sql += " and (? < b.CheckOutDate and ? > b.CheckInDate)";
            } else if (checkin == null) {
                sql += " and ? > b.CheckInDate ";
            } else if (checkout == null) {
                sql += " and ? < b.CheckOutDate ";
            }
            sql += " )";
        }
//        if (sortBy != null && !sortBy.trim().isEmpty()) {
//            sql += "ORDER BY " + sortBy + (isAsc ? " ASC " : " DESC ");
//        }

//        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            int count = 1;
            stm.setBoolean(count++, isDeleted);
            if (status != null && !status.equalsIgnoreCase("all")) {
                stm.setString(count++, status);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                stm.setString(count++, kw);
                stm.setString(count++, kw);
                stm.setString(count++, kw);
                stm.setString(count++, kw);
            }
            if (roomTypeId != -1) {
                stm.setInt(count++, roomTypeId);
            }
            if (from != -1.0 || to != -1.0) {
                if (from != -1 && to != -1) {
                    stm.setDouble(count++, from);
                    stm.setDouble(count++, to);
                } else if (from == -1.0) {
                    stm.setDouble(count++, to);
                } else if (to == -1) {
                    stm.setDouble(count++, from);
                }
            }
            if (numberPeople != -1) {
                stm.setInt(count++, numberPeople);
            }
            if (checkin != null || checkout != null) {
                if (checkin != null && checkout != null) {

                    stm.setDate(count++, checkin);
                    stm.setDate(count++, checkout);
                } else if (checkin == null) {

                    stm.setDate(count++, checkout);
                } else if (checkout == null) {

                    stm.setDate(count++, checkin);
                }
            }

//            stm.setInt(count++, (pageIndex - 1) * pageSize);
//            stm.setInt(count++, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(roomTypeId);

                list.add(new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
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

    public Room getRoomByRoomID(int id) {
        String sql = "select * from Room where RoomID =?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(rs.getInt("roomtypeid"));
                return new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
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

    public List<Room> getAllRoom() {
        List<Room> list = new ArrayList<>();
        String sql = "Select * from room";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(rs.getInt("roomtypeid"));
                list.add(new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
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

    public int countNumberRoom() {
        int count = 0;
        String sql = "select count(*) from room";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    //CRUD ROOM
    public boolean updateRoom(Room room) {
        String roomSql = "UPDATE Room "
                + "SET RoomNumber = ?, "
                + "Status = ?, "
                + "roomTypeID = ?, "
                + "Price = ?, "
                + "UpdatedAt = GETDATE() "
                + "WHERE RoomID = ?";
        String roomDetailSql = "UPDATE RoomDetail "
                + "SET bedType = ?, "
                + "Area = ?, "
                + "MaxGuest = ?, "
                + "Description = ?, "
                + "UpdatedAt = GETDATE() "
                + "WHERE RoomDetailID = ( SELECT RoomDetailID FROM Room WHERE RoomID = ?);";
        try {
            connection.setAutoCommit(false);

            //update table room
            try (PreparedStatement roomPre = connection.prepareStatement(roomSql)) {
                roomPre.setString(1, room.getRoomNumber());
                roomPre.setString(2, room.getStatus());
                roomPre.setInt(3, room.getRoomTypeID().getRoomTypeID());
                roomPre.setDouble(4, room.getPrice());
                roomPre.setDouble(5, room.getRoomID());
                roomPre.executeUpdate();
            }
            try (PreparedStatement roomDetailPre = connection.prepareStatement(roomDetailSql)) {
                RoomDetail detail = room.getRoomDetail();
                roomDetailPre.setString(1, detail.getBedType());
                roomDetailPre.setDouble(2, detail.getArea());
                roomDetailPre.setInt(3, detail.getMaxGuest());
                roomDetailPre.setString(4, detail.getDescription());
                roomDetailPre.setInt(5, room.getRoomID());
                roomDetailPre.executeUpdate();
            }
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateDeleteRoom(int roomid, int deleteBy, boolean isDeleted) {
        String sql = "UPDATE Room\n"
                + "SET IsDeleted = ?,\n"
                + "    DeletedAt = GETDATE(),\n"
                + "    DeletedBy = ?\n"
                + "WHERE RoomID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setBoolean(1, isDeleted);
            pre.setInt(2, deleteBy);
            pre.setInt(3, roomid);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean insertRoom(Room room) {
        String insertRoomDetailSQL = "INSERT INTO RoomDetail (BedType, Area, MaxGuest, Description, CreatedAt) "
                + "VALUES (?, ?, ?, ?, GETDATE())";

        String insertRoomSQL = "INSERT INTO Room (RoomNumber, RoomDetailID, RoomTypeID, Status, Price, HotelID, CreatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Insert RoomDetail
            int roomDetailID;
            try (PreparedStatement ps1 = connection.prepareStatement(insertRoomDetailSQL)) {
                ps1.setString(1, room.getRoomDetail().getBedType());
                ps1.setDouble(2, room.getRoomDetail().getArea());
                ps1.setInt(3, room.getRoomDetail().getMaxGuest());
                ps1.setString(4, room.getRoomDetail().getDescription());

                int affectedRows = ps1.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }

                try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        roomDetailID = generatedKeys.getInt(1);
                    } else {
                        connection.rollback();
                        return false;
                    }
                }
            }

            // 2. Insert Room
            try (PreparedStatement ps2 = connection.prepareStatement(insertRoomSQL)) {
                ps2.setString(1, room.getRoomNumber());
                ps2.setInt(2, roomDetailID);
                ps2.setInt(3, room.getRoomTypeID().getRoomTypeID());
                ps2.setString(4, room.getStatus());
                ps2.setDouble(5, room.getPrice());
                ps2.setInt(6, room.getHotel().getHotelID());

                ps2.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public static void main(String[] args) {
        List<Room> list = new dao.RoomDAO().getListRoom(null, null, 0, 4400, 1, -1, "", "Available", "r.RoomID", true, 0, 10, false);
        for (Room room : list) {
            System.out.println(room.getRoomTypeID());
        }
//        System.out.println(new dao.RoomDAO().getRoomByRoomID(5).getPrice());
//        Room room = new Room(1, "hieu", new RoomDetail(1, "hieu", 123, 12, "Anh hhieu dep trai"), new dao.RoomTypeDAO().getRoomTypeById(1), "Available", 200);
//        boolean a = new dao.RoomDAO().updateRoom(room);
//        if (a) {
//            System.out.println("asfljahsfg");
//        } else {
//            System.out.println("kajgb");
//        }
//        if (new dao.RoomDAO().updateDeleteRoom(1, 1, true)) {
//            System.out.println("afjkbnsg ");
//        }
    }

}
