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
import entity.RoomImage;
import entity.RoomType;
import java.sql.Statement;
import java.sql.Date;
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

    public static void main(String[] args) {
        List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null,
                0, 100000,
                0, -1,
                "studio", "all", "price",
                true, 4, 6, null);
        for (Room room : listRoom) {
            System.out.println(room.getRoomTypeID());
        }
        RoomDetail detail = new RoomDetail();
        detail.setBedType("Queen");
        detail.setArea(28.5);
        detail.setMaxGuest(2);
        detail.setDescription("Phòng có ban công, view đẹp");

        // 2. Tạo RoomType (giả định ID = 1)
        RoomType type = new RoomType();
        type.setRoomTypeID(1); // bạn cần đảm bảo roomTypeID = 1 tồn tại trong DB

        // 3. Tạo Hotel (giả định ID = 1)
        Hotel hotel = new Hotel();
        hotel.setHotelID(1); // bạn cần đảm bảo hotelID = 1 tồn tại trong DB

        // 4. Tạo Room
        Room room = new Room();
        room.setRoomNumber("B101");
        room.setRoomDetail(detail);
        room.setRoomTypeID(type);
        room.setStatus("Available");
        room.setPrice(950000);
        room.setHotel(hotel);

        // 5. Tạo danh sách ảnh
        List<RoomImage> listImg = new ArrayList<>();
        RoomImage img1 = new RoomImage();
        img1.setImageURL("data:image/jpeg;base64,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");
        img1.setCaption("Phòng chính");

        RoomImage img2 = new RoomImage();
        img2.setImageURL("data:image/jpeg;base64,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");
        img2.setCaption("Nhà tắm");

        listImg.add(img1);
        listImg.add(img2);

        // 6. Gọi DAO
        RoomDAO dao = new RoomDAO();
        boolean result = dao.addRoom(room, listImg);

        // 7. Kết quả
        if (result) {
            System.out.println("Thêm phòng thành công!");
        } else {
            System.out.println("Thêm phòng thất bại!");
        }

    }
}
