/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.BookingInfo;
import entity.Hotel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Room;
import entity.RoomDetail;
import entity.RoomImage;
import entity.RoomInfo;
import entity.RoomType;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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
            Boolean isDeleted
    ) {
        List<Room> list = new ArrayList();
        String sql = "select r.RoomID , r.RoomNumber , r.RoomDetailID, r.RoomTypeID , r.Status , r.Price, r.HotelID , r.CreatedAt , r.UpdatedAt,r.DeletedAt , r.DeletedBy,r.IsDeleted\n"
                + "from Room r\n"
                + "inner join roomtype rt on rt.RoomTypeID = r.RoomTypeID\n"
                + "inner join RoomDetail rd on rd.RoomDetailID = r.RoomDetailID\n"
                + "where 1=1 \n";
        if (isDeleted != null) {
            sql += " and r.IsDeleted = ? ";
        }
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
            sql += " and rd.MaxGuest >= ? ";
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
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            sql += "ORDER BY r." + sortBy + (isAsc ? " ASC " : " DESC ");
        }

        System.out.println("Final SQL: " + sql);
//        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            int count = 1;
            if (isDeleted != null) {
                stm.setBoolean(count++, isDeleted);
            }
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

    public List<Room> getRoomsByPage(int offset, int pageSize) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT r.*, rt.TypeName, rd.BedType, rd.Area, rd.MaxGuest "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 "
                + "ORDER BY r.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                r.setPrice(rs.getDouble("Price"));
                r.setCreatedAt(rs.getTimestamp("CreatedAt"));
                r.setRoomTypeID(new RoomType());
                r.getRoomTypeID().setTypeName(rs.getString("TypeName"));
                r.setRoomDetail(new RoomDetail());
                r.getRoomDetail().setBedType(rs.getString("BedType"));
                r.getRoomDetail().setArea(rs.getFloat("Area"));
                r.getRoomDetail().setMaxGuest(rs.getInt("MaxGuest"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countAllRooms() {
        String sql = "SELECT COUNT(*) FROM Room WHERE IsDeleted = 0";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Room> searchRoomsByPage(String keyword, int offset, int pageSize) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT r.*, rt.TypeName, rd.BedType, rd.Area, rd.MaxGuest "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 AND (r.RoomNumber LIKE ? OR rt.TypeName LIKE ? OR rd.BedType LIKE ?) "
                + "ORDER BY r.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setInt(4, offset);
            stmt.setInt(5, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                r.setPrice(rs.getDouble("Price"));
                r.setCreatedAt(rs.getTimestamp("CreatedAt"));
                r.setRoomTypeID(new RoomType());
                r.getRoomTypeID().setTypeName(rs.getString("TypeName"));
                r.setRoomDetail(new RoomDetail());
                r.getRoomDetail().setBedType(rs.getString("BedType"));
                r.getRoomDetail().setArea(rs.getFloat("Area"));
                r.getRoomDetail().setMaxGuest(rs.getInt("MaxGuest"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchRooms(String keyword) {
        String sql = "SELECT COUNT(*) "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 AND (r.RoomNumber LIKE ? OR rt.TypeName LIKE ? OR rd.BedType LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteRoom(int id, int deletedBy) {
        String sql = "UPDATE Room SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE RoomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deletedBy);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
        }
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
    public boolean updateRoom(Room room, List<RoomImage> listImgs) {
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
        String deleteImageSql = "DELETE FROM RoomImage WHERE RoomDetailID = ?";
        String insertImageSql = "INSERT INTO RoomImage (RoomDetailID, ImageURL, Caption, CreatedAt) "
                + "VALUES (?, ?, ?, GETDATE())";

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
            // Xóa ảnh cũ
            try (PreparedStatement delImgPre = connection.prepareStatement(deleteImageSql)) {
                delImgPre.setInt(1, room.getRoomDetail().getRoomDetailID());
                delImgPre.executeUpdate();
            }

            // 3. Insert RoomImage (nếu có)
            if (listImgs != null && !listImgs.isEmpty()) {
                try (PreparedStatement ps3 = connection.prepareStatement(insertImageSql)) {
                    for (RoomImage img : listImgs) {
                        ps3.setInt(1, room.getRoomDetail().getRoomDetailID());
                        ps3.setString(2, img.getImageURL());
                        ps3.setString(3, img.getCaption() != null ? img.getCaption() : "");
                        ps3.addBatch();
                    }
                    ps3.executeBatch();
                }
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

    public boolean addRoom(Room room, List<RoomImage> listImg) {
        String insertRoomDetailSQL = "INSERT INTO RoomDetail (BedType, Area, MaxGuest, Description, CreatedAt) "
                + "VALUES (?, ?, ?, ?, GETDATE())";

        String insertRoomSQL = "INSERT INTO Room (RoomNumber, RoomDetailID, RoomTypeID, Status, Price, HotelID, CreatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";

        String insertImageSQL = "INSERT INTO RoomImage (RoomDetailID, ImageURL, Caption, CreatedAt) "
                + "VALUES (?, ?, ?, GETDATE())";

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            int roomDetailID;

            // 1. Insert RoomDetail
            try (PreparedStatement ps1 = connection.prepareStatement(insertRoomDetailSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, room.getRoomDetail().getBedType());
                ps1.setDouble(2, room.getRoomDetail().getArea());
                ps1.setInt(3, room.getRoomDetail().getMaxGuest());
                ps1.setString(4, room.getRoomDetail().getDescription());

                int affectedRows = ps1.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }

                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (rs.next()) {
                        roomDetailID = rs.getInt(1);
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

            // 3. Insert RoomImage (nếu có)
            if (listImg != null && !listImg.isEmpty()) {
                try (PreparedStatement ps3 = connection.prepareStatement(insertImageSQL)) {
                    for (RoomImage img : listImg) {
                        ps3.setInt(1, roomDetailID);
                        ps3.setString(2, img.getImageURL());
                        ps3.setString(3, img.getCaption() != null ? img.getCaption() : "");
                        ps3.addBatch();
                    }
                    ps3.executeBatch();
                }
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

    public Map<Integer, List<RoomInfo>> getRoomsGroupedByFloor() {
        Map<Integer, List<RoomInfo>> map = new LinkedHashMap<>();

        String sql = """
        WITH BookingCTE AS (
            SELECT bd.RoomID, b.BookingID, b.CheckInDate, b.CheckOutDate, b.Status AS BookingStatus,
                   u.FirstName, u.LastName,
                   ROW_NUMBER() OVER (
                       PARTITION BY bd.RoomID 
                       ORDER BY 
                           CASE     
                               WHEN b.Status = 'Checked-in' THEN 1
                               WHEN b.Status = 'Confirmed' THEN 2
                               WHEN b.Status = 'Pending' THEN 3
                               WHEN b.Status = 'Checked-out' THEN 4
                               ELSE 99 -- các trạng thái khác ưu tiên thấp
                           END,
                           b.CheckInDate DESC
                   ) AS rn
            FROM BookingDetail bd
            JOIN Booking b ON bd.BookingID = b.BookingID AND b.IsDeleted = 0
            LEFT JOIN [User] u ON b.UserID = u.UserID
            WHERE bd.IsDeleted = 0 AND CAST(b.CheckOutDate AS DATE) >= CAST(GETDATE() AS DATE) AND b.status != 'Cancelled'
        )

        SELECT r.RoomID, r.RoomNumber, r.Status AS RoomStatus, r.Price,
               rt.TypeName AS RoomType,
               f.FloorNumber,
               bc.BookingID, bc.CheckInDate, bc.CheckOutDate,
               bc.BookingStatus,
               bc.FirstName, bc.LastName
        FROM Room r
        JOIN Floor f ON r.FloorID = f.FloorID
        JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID
        LEFT JOIN BookingCTE bc ON bc.RoomID = r.RoomID AND bc.rn = 1
        WHERE r.IsDeleted = 0
        ORDER BY f.FloorNumber, r.RoomNumber
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RoomInfo room = new RoomInfo();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setStatus(rs.getString("RoomStatus"));
                room.setPrice(rs.getDouble("Price"));
                room.setRoomType(rs.getString("RoomType"));
                room.setFloorNumber(rs.getInt("FloorNumber"));

                // Thông tin khách nếu có
                String guestName = ((rs.getString("FirstName") != null ? rs.getString("FirstName") : "") + " "
                        + (rs.getString("LastName") != null ? rs.getString("LastName") : "")).trim();
                room.setGuestName(guestName.isBlank() ? null : guestName);
                room.setCheckInDate(rs.getTimestamp("CheckInDate"));
                room.setCheckOutDate(rs.getTimestamp("CheckOutDate"));
                room.setBookingStatus(rs.getString("BookingStatus"));

                int floor = rs.getInt("FloorNumber");
                map.computeIfAbsent(floor, k -> new ArrayList<>()).add(room);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<BookingInfo> getAllBookingsByRoomId(int roomId) {
        List<BookingInfo> list = new ArrayList<>();

        String sql = """
        SELECT b.BookingID, b.CheckInDate, b.CheckOutDate, b.Status,
               u.FirstName, u.LastName
        FROM BookingDetail bd
        JOIN Booking b ON bd.BookingID = b.BookingID AND b.IsDeleted = 0
        JOIN [User] u ON b.UserID = u.UserID AND u.IsDeleted = 0
        WHERE bd.RoomID = ? AND bd.IsDeleted = 0 and CheckOutDate >= getdate()
        ORDER BY b.CheckInDate DESC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookingInfo booking = new BookingInfo();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setCheckInDate(rs.getTimestamp("CheckInDate"));
                booking.setCheckOutDate(rs.getTimestamp("CheckOutDate"));
                booking.setStatus(rs.getString("Status"));

                String fullName = ((rs.getString("FirstName") != null ? rs.getString("FirstName") : "") + " "
                        + (rs.getString("LastName") != null ? rs.getString("LastName") : "")).trim();
                booking.setGuestName(fullName);

                list.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public RoomInfo getRoomInfo(int roomId) {
        String sql = """
        SELECT r.RoomID, r.RoomNumber, r.Status, r.Price,
               rt.TypeName AS RoomType,
               f.FloorNumber
        FROM Room r
        JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID
        JOIN Floor f ON r.FloorID = f.FloorID
        WHERE r.RoomID = ? AND r.IsDeleted = 0
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RoomInfo room = new RoomInfo();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setStatus(rs.getString("Status"));
                room.setPrice(rs.getDouble("Price"));
                room.setRoomType(rs.getString("RoomType"));
                room.setFloorNumber(rs.getInt("FloorNumber"));
                return room;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateRoomStatus(int roomid, String status) {
        String sql = "update room\n"
                + "set status = ? \n"
                + "where RoomID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomid);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public BigDecimal getRoomPrice(int roomId) {
        String sql = "select Price from Room\n"
                   + "where RoomID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("Price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;  // hoặc null hoặc tùy xử lý khi không có phòng
    }
}
