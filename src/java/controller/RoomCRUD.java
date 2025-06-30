/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Hotel;
import entity.Room;
import entity.RoomDetail;
import entity.RoomImage;
import entity.RoomType;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import validation.Validation;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB mỗi file
        maxRequestSize = 1024 * 1024 * 20 // 20MB cho toàn bộ request
)
@WebServlet(name = "RoomCRUD", urlPatterns = {"/roomcrud"})
public class RoomCRUD extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RoomCRUD</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomCRUD at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "delete":
                    deleteRoom(request, response);
                    break;
                case "deleteMultiple":
                    deleteMultipleRoom(request, response);
                    break;
                case "filterRoom":
                    filter(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addRoom(request, response);
                    break;
                case "edit":
                    editRoom(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void editRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomIdRaw = request.getParameter("roomID");
        String roomDetailRaw = request.getParameter("roomDetail");
        String roomNumber = request.getParameter("roomNumber");
        String roomTypeIdRaw = request.getParameter("roomTypeID");
        String status = request.getParameter("status");
        String priceRaw = request.getParameter("price");
        String bedType = request.getParameter("bedType");
        String description = request.getParameter("description");
        String maxGuestRaw = request.getParameter("maxGuest");
        String areaRaw = request.getParameter("area");

        RoomDetail roomD = new RoomDetail(Validation.parseStringToInt(roomDetailRaw),
                bedType,
                Validation.parseStringToDouble(areaRaw),
                Validation.parseStringToInt(maxGuestRaw),
                description);
        RoomType roomT = new dao.RoomTypeDAO().getRoomTypeById(Validation.parseStringToInt(roomTypeIdRaw));
        Room room = new Room(Validation.parseStringToInt(roomIdRaw),
                roomNumber, roomD,
                roomT,
                status, Validation.parseStringToDouble(priceRaw));

        Collection<Part> parts = request.getParts();
        List<RoomImage> listImg = new ArrayList<>();

        for (Part part : parts) {
            if ("photos".equals(part.getName()) && part.getSize() > 0) {
                String originalFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // lấy đuôi .jpg, .png, ...

                // ➕ Đổi tên file theo timestamp + roomNumber
                long timestamp = System.currentTimeMillis(); // hoặc timestamp bạn có
                Date date = new Date(timestamp);
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmssddMMyyyy");
                String newFileName = "room_" + roomNumber + "_" + sdf.format(date) + extension;

                // BUILD
                String uploadPath = getServletContext().getRealPath("/") + "img";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Ghi file mới vào thư mục
                try (InputStream input = part.getInputStream()) {
                    Files.copy(
                            input,
                            Paths.get(uploadPath, newFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    e.printStackTrace(); // kiểm tra lỗi thật sự
                }

                // Tạo RoomImage object
                RoomImage image = new RoomImage();
                image.setImageURL("img/" + newFileName);
                image.setCaption(uploadPath); // hoặc bạn có thể truyền caption riêng nếu cần
                listImg.add(image);
            }
        }
        boolean checkUpdate = new dao.RoomDAO().updateRoom(room, listImg);
        List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null, 0, 100000, 0, -1, "", "all", "", false, 4, 6, false);
        request.setAttribute("listRoom", listRoom);
        request.setAttribute("numberRoom", listRoom.size());
        request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
        //CẦN SỬA KHI KHÔNG UPDATE ĐƯỢC
        if (checkUpdate) {
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        }
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomNumber = request.getParameter("roomNumber");
        String roomTypeIdRaw = request.getParameter("roomTypeID");
        String status = request.getParameter("status");
        String hotelRaw = request.getParameter("hotel");
        String priceRaw = request.getParameter("price");
        String bedType = request.getParameter("bedType");
        String description = request.getParameter("description");
        String maxGuestRaw = request.getParameter("maxGuest");
        String areaRaw = request.getParameter("area");

        RoomDetail detail = new RoomDetail(bedType, Validation.parseStringToDouble(areaRaw), Validation.parseStringToInt(maxGuestRaw), description);
        RoomType type = new RoomType();
        type.setRoomTypeID(Validation.parseStringToInt(roomTypeIdRaw));
        Hotel hotel = new Hotel();
        hotel.setHotelID(Validation.parseStringToInt(hotelRaw));
        Room room = new Room(roomNumber, detail, type, status, Validation.parseStringToDouble(priceRaw), hotel);

        Collection<Part> parts = request.getParts();
        List<RoomImage> listImg = new ArrayList<>();

        for (Part part : parts) {
            if ("photos".equals(part.getName()) && part.getSize() > 0) {
                String originalFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // lấy đuôi .jpg, .png, ...

                // ➕ Đổi tên file theo timestamp + roomNumber
                long timestamp = System.currentTimeMillis(); // hoặc timestamp bạn có
                Date date = new Date(timestamp);
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmssddMMyyyy");
                String newFileName = "room_" + roomNumber + "_" + sdf.format(date) + extension;

                // BUILD
                String uploadPath = getServletContext().getRealPath("/") + "img";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String runningPath = getServletContext().getRealPath("/");

                File projectRoot = Paths.get(runningPath).getParent().getParent().toFile();

                File targetImgDir = new File(projectRoot, "web/img");

                if (!targetImgDir.exists()) {
                    targetImgDir.mkdirs();
                }

                String uploadPath1 = targetImgDir.getAbsolutePath();

                // Ghi file mới vào thư mục
                try (InputStream input = part.getInputStream()) {
                    Files.copy(
                            input,
                            Paths.get(uploadPath, newFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                    
                } catch (IOException e) {
                    e.printStackTrace(); // kiểm tra lỗi thật sự
                }
                
                try (InputStream input = part.getInputStream()) {
                    Files.copy(
                            input,
                            Paths.get(uploadPath1, newFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                    
                } catch (IOException e) {
                    e.printStackTrace(); // kiểm tra lỗi thật sự
                }

                // Tạo RoomImage object
                RoomImage image = new RoomImage();
                image.setImageURL("img/" + uploadPath);
                image.setCaption(uploadPath1); // hoặc bạn có thể truyền caption riêng nếu cần
                listImg.add(image);
            }
        }
        boolean check = new dao.RoomDAO().addRoom(room, listImg);
        if (check) {
            List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null, 0, 100000, 0, -1, "", "all", "", false, 4, 6, false);
            request.setAttribute("listRoom", listRoom);
            request.setAttribute("numberRoom", listRoom.size());
            request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        }
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomId = request.getParameter("roomId");
        if (new dao.RoomDAO().updateDeleteRoom(Validation.parseStringToInt(roomId), 1, true)) {
            List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null, 0, 100000, 0, -1, "", "all", "", false, 4, 6, false);
            request.setAttribute("listRoom", listRoom);
            request.setAttribute("numberRoom", listRoom.size());
            request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        }

    }

    private void deleteMultipleRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] roomIdsRaw = request.getParameterValues("roomIds");
        int number = roomIdsRaw.length;
        String errorRoom = "";
        if (roomIdsRaw == null || roomIdsRaw.length == 0) {
            request.setAttribute("deleteError", "No rooms selected for deletion.");
        } else {
            for (int i = 0; i < number; i++) {
                int roomId = Validation.parseStringToInt(roomIdsRaw[i]);
                boolean check = new dao.RoomDAO().updateDeleteRoom(roomId, 1, true);
                if (!check) {
                    Room room = new dao.RoomDAO().getRoomByRoomID(roomId);
                    errorRoom += room.getRoomNumber();
                }
            }
        }
        request.setAttribute("deleteError", "Can't delete room: " + errorRoom);
        List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null, 0, 100000, 0, -1, "", "all", "", false, 4, 6, false);
        request.setAttribute("listRoom", listRoom);
        request.setAttribute("numberRoom", listRoom.size());
        request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
        request.getRequestDispatcher("manageroom.jsp").forward(request, response);
    }

    private void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyWorld = request.getParameter("keyWorld");
        String roomType = request.getParameter("roomType");
        String sortBy = request.getParameter("sortBy");
        String sort = request.getParameter("sort");
        boolean check = sort.equalsIgnoreCase("asc");
        String viewDeleted = request.getParameter("presentDeleted");
        Boolean isDeleted = true;
        if ("1".equalsIgnoreCase(viewDeleted)) {
            isDeleted = null;
        } else {
            isDeleted = false;
        }

        List<Room> listRoom = new dao.RoomDAO().getListRoom(null, null,
                0, 100000, 0, Validation.parseStringToInt(roomType),
                keyWorld, "all", sortBy, check,
                4, 6, isDeleted);

        request.setAttribute("keyWorld", keyWorld);
        request.setAttribute("roomType", roomType);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sort", sort);
        request.setAttribute("presentDeleted", viewDeleted);
        request.setAttribute("listRoom", listRoom);
        request.setAttribute("numberRoom", listRoom.size());
        request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
        request.getRequestDispatcher("manageroom.jsp").forward(request, response);
    }

}
