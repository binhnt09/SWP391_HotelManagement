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

    public List<Room> getListRoom(Date checkin, Date checkout, double from, double to, int numberPeople, int roomTypeID) {
        List<Room> list = new ArrayList();
        String sql = "select r.RoomID , r.RoomNumber , r.RoomDetailID, r.RoomTypeID , r.Status , r.Price, r.HotelID , r.CreatedAt , r.UpdatedAt,r.DeletedAt , r.DeletedBy,r.IsDeleted\n"
                + "from Room r\n"
                + "inner join roomtype rt on rt.RoomTypeID = r.RoomTypeID\n"
                + "inner join RoomDetail rd on rd.RoomDetailID = r.RoomDetailID\n"
                + "where r.Status = 'available'\n";
        if (roomTypeID != -1) {
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
            sql += ")";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            int count = 1;

            if (roomTypeID != -1) {
                stm.setInt(count++, roomTypeID);
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
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(roomTypeID);

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
    public List<Room> getListRoom(Date checkin, Date checkout,
                              double from, double to,
                              int numberPeople, int roomTypeID,
                              String searchText, String status,
                              String sortBy, boolean isAsc,
                              int pageIndex, int pageSize) {
    List<Room> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT r.RoomID, r.RoomNumber, r.RoomDetailID, r.RoomTypeID, r.Status, r.Price, ")
       .append("r.HotelID, r.CreatedAt, r.UpdatedAt, r.DeletedAt, r.DeletedBy, r.IsDeleted ")
       .append("FROM Room r ")
       .append("INNER JOIN RoomType rt ON rt.RoomTypeID = r.RoomTypeID ")
       .append("INNER JOIN RoomDetail rd ON rd.RoomDetailID = r.RoomDetailID ")
       .append("WHERE r.IsDeleted = 0 ");

    List<Object> params = new ArrayList<>();
    
    if (roomTypeID != -1) {
        sql.append("AND r.RoomTypeID = ? ");
        params.add(roomTypeID);
    }
    if (from != -1.0 || to != -1.0) {
        if (from != -1 && to != -1) {
            sql.append("AND r.Price BETWEEN ? AND ? ");
            params.add(from);
            params.add(to);
        } else if (from == -1.0) {
            sql.append("AND r.Price <= ? ");
            params.add(to);
        } else if (to == -1) {
            sql.append("AND r.Price >= ? ");
            params.add(from);
        }
    }
    if (numberPeople != -1) {
        sql.append("AND rd.MaxGuest >= ? ");
        params.add(numberPeople);
    }
    if (searchText != null && !searchText.trim().isEmpty()) {
        sql.append("AND (r.RoomNumber LIKE ? OR rd.BedType LIKE ? OR rd.Description LIKE ?) ");
        String like = "%" + searchText + "%";
        params.add(like);
        params.add(like);
        params.add(like);
    }
    if (status != null && !status.equalsIgnoreCase("all")) {
        sql.append("AND r.Status = ? ");
        params.add(status);
    }
    if (checkin != null || checkout != null) {
        sql.append("AND r.RoomID NOT IN ( ")
           .append("SELECT bd.RoomID FROM BookingDetail bd ")
           .append("INNER JOIN Booking b ON b.BookingID = bd.BookingID ")
           .append("WHERE 1=1 ");
        if (checkin != null && checkout != null) {
            sql.append("AND (? < b.CheckOutDate AND ? > b.CheckInDate) ");
            params.add(checkin);
            params.add(checkout);
        } else if (checkin == null) {
            sql.append("AND ? > b.CheckInDate ");
            params.add(checkout);
        } else if (checkout == null) {
            sql.append("AND ? < b.CheckOutDate ");
            params.add(checkin);
        }
        sql.append(") ");
    }

    // Validate and append ORDER BY
    Set<String> validSortColumns = Set.of("Price", "Area", "RoomNumber");
    if (sortBy != null && validSortColumns.contains(sortBy)) {
        sql.append("ORDER BY ").append(sortBy).append(isAsc ? " ASC " : " DESC ");
    } else {
        sql.append("ORDER BY r.RoomID ASC "); // default
    }

    // Pagination
    sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
    params.add((pageIndex - 1) * pageSize); // offset
    params.add(pageSize); // limit

    try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof Integer) {
                stm.setInt(i + 1, (Integer) param);
            } else if (param instanceof Double) {
                stm.setDouble(i + 1, (Double) param);
            } else if (param instanceof String) {
                stm.setString(i + 1, (String) param);
            } else if (param instanceof Date) {
                stm.setDate(i + 1, (Date) param);
            }
        }

        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Hotel hotel = new HotelDAO().getHotelByID(rs.getInt("HotelID"));
            RoomDetail roomDetail = new RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
            RoomType roomType = new RoomTypeDAO().getRoomTypeById(rs.getInt("RoomTypeID"));

            list.add(new Room(
                    rs.getInt("RoomID"),
                    rs.getString("RoomNumber"),
                    roomDetail,
                    roomType,
                    rs.getString("Status"),
                    rs.getDouble("Price"),
                    hotel,
                    rs.getTimestamp("CreatedAt"),
                    rs.getTimestamp("UpdatedAt"),
                    rs.getTimestamp("DeletedAt"),
                    rs.getInt("DeletedBy"),
                    rs.getBoolean("IsDeleted")
            ));
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

    public static void main(String[] args) {
//        List<Room> list = new dao.RoomDAO().getListRoom(null, null, -1, -1, -1, 1);
//        for (Room room : list) {
//            System.out.println(room.getRoomTypeID());
//        }
        System.out.println(new dao.RoomDAO().getRoomByRoomID(5).getPrice());
        Room room = new Room(1, "hieu", new RoomDetail(1, "hieu", 123, 12, "Anh hhieu dep trai"), new dao.RoomTypeDAO().getRoomTypeById(1), "Available", 200);
        boolean a = new dao.RoomDAO().updateRoom(room);
        if(a){
            System.out.println("asfljahsfg");
        }else{
            System.out.println("kajgb");
        }
    }

}
