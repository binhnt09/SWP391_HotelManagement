/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import com.google.gson.Gson;
import dao.RoomDAO;
import dao.RoomTypeDAO;
import entity.Amenity;
import entity.Hotel;
import entity.Room;
import entity.RoomDetail;
import entity.RoomImage;
import entity.RoomType;
import entity.Service;
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
import jakarta.servlet.http.HttpSession;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

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
                case "deleteRoomType":
                    deleteRoomType(request, response);
                    break;
                case "checkBookingStatus":
                    checkBookingStatus(request, response);
                    break;
                case "filterRoom":
                    filter(request, response);
                    break;
                case "getImages":
                    getRoomImg(request, response);
                    break;
                case "checkRoomNumber":
                    checkRoomNumber(request, response);
                    break;
//                case "checkRoomTypeName":
//                    checkRoomTypeName(request, response);
//                    break;
                case "getAllAmenity":
                    getAllAmenity(request, response);
                    break;
                case "getAllService":
                    getAllService(request, response);
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
            HttpSession session = request.getSession();

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addRoom(request, response);
                    session.setAttribute("openTab", "#managerRoom");
                    break;
                case "edit":
                    editRoom(request, response);
                    session.setAttribute("openTab", "#managerRoom");
                    break;

                case "editRoomType":
                    editRoomType(request, response);
                    session.setAttribute("openTab", "#managerRoomType");
                    break;
                case "addRoomType":
                    addRoomType(request, response);
                    session.setAttribute("openTab", "#managerRoomType");
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            
            response.sendRedirect("manageroom");
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
        String description = request.getParameter("description");
//        String maxGuestRaw = request.getParameter("maxGuest");
        String areaRaw = request.getParameter("area");

        int typeId = Validation.parseStringToInt(roomTypeIdRaw);
        RoomDetail roomD = new RoomDetail(Validation.parseStringToInt(roomDetailRaw),
                "Family",
                Validation.parseStringToDouble(areaRaw),
                new dao.RoomTypeDAO().getRoomTypeById(typeId).getNumberPeople(),
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
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

//                String uploadPath = getServletContext().getRealPath("/") + "img";
                String uniqueId = UUID.randomUUID().toString();
                String newFileName = "room_" + roomNumber + "_" + uniqueId + extension;

//                String uploadPath = getServletContext().getRealPath("/");
                String uploadPath = getServletContext().getRealPath("/") + "img";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                try (InputStream input = part.getInputStream()) {
                    Files.copy(
                            input,
                            Paths.get(uploadPath, newFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Tạo RoomImage object
                RoomImage image = new RoomImage();
                image.setImageURL("img/" + newFileName);
                image.setCaption(uploadPath);
                listImg.add(image);
            }
        }
        boolean checkUpdate = new dao.RoomDAO().updateRoom(room, listImg);
        String[] imageIdRaw = request.getParameterValues("imagesToDelete");
        List<RoomImage> imgsToDelete = new ArrayList<>();
        if (imageIdRaw != null) {
            List<Integer> imageIds = new ArrayList<>();
            for (String id : imageIdRaw) {
                int tmp = Validation.parseStringToInt(id);
                if (tmp > 0) {
                    imageIds.add(tmp);
                    RoomImage img = new dao.RoomImageDAO().getImgByImgId(tmp);
                    if (img != null) {
                        imgsToDelete.add(img);
                    } else {
                        System.out.println("Không tìm thấy ảnh với ID = " + tmp);
                    }
                }
            }
            for (RoomImage img : imgsToDelete) {
                String realPath = getServletContext().getRealPath("/") + img.getImageURL(); // ví dụ: img/room_xyz.png
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }

            new dao.RoomImageDAO().deleteRoomImages(imageIds);

        }
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomNumber = request.getParameter("roomNumber");
        String roomTypeIdRaw = request.getParameter("roomTypeID");
        String status = request.getParameter("status");
//        String hotelRaw = request.getParameter("hotel");
        String priceRaw = request.getParameter("price");
//        String bedType = request.getParameter("bedType");
        String description = request.getParameter("description");
//        String maxGuestRaw = request.getParameter("maxGuest");
        String areaRaw = request.getParameter("area");

        int typeId = Validation.parseStringToInt(roomTypeIdRaw);

        RoomType type = new dao.RoomTypeDAO().getRoomTypeById(typeId);
        RoomDetail detail = new RoomDetail(type.getTypeName(), Validation.parseStringToDouble(areaRaw), type.getNumberPeople(), description);
        Hotel hotel = new Hotel();
        hotel.setHotelID(1);
        Room room = new Room(roomNumber, detail, type, status, Validation.parseStringToDouble(priceRaw), hotel);

        Collection<Part> parts = request.getParts();
        List<RoomImage> listImg = new ArrayList<>();

        for (Part part : parts) {
            if ("photos".equals(part.getName()) && part.getSize() > 0) {
                String originalFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

                String uniqueId = UUID.randomUUID().toString();
                String newFileName = "room_" + roomNumber + "_" + uniqueId + extension;

                String uploadPath = getServletContext().getRealPath("/") + "img";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                try (InputStream input = part.getInputStream()) {
                    Files.copy(
                            input,
                            Paths.get(uploadPath, newFileName),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RoomImage image = new RoomImage();
                image.setImageURL(uploadPath + newFileName);
                image.setCaption(uploadPath);
                listImg.add(image);
            }
        }
        boolean check = new dao.RoomDAO().addRoom(room, listImg);
//             Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
//         if(check){
//             new dao.NotificationDao().addNotifications(auth.getUser().getUserId(), "Bạn đã thêm phòng thành công", "Sucsess");
//         }else{
//             new dao.NotificationDao().addNotifications(auth.getUser().getUserId(), "Thêm phòng không thành công", "Error");
//         }
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) {
        String roomIdStr = request.getParameter("roomId");
        int roomId = Validation.parseStringToInt(roomIdStr);
        RoomDAO roomDAO = new RoomDAO();

        JSONObject responseJson = new JSONObject();

        if (roomDAO.isRoomBooked(roomId)) {
            responseJson.put("status", "booked");
        } else if (roomDAO.updateDeleteRoom(roomId, 1, true)) {
            responseJson.put("status", "success");
        } else {
            responseJson.put("status", "fail");
        }

        response.setContentType("application/json");
        try {
            response.getWriter().write(responseJson.toString());
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //CHƯA CHECK
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

        HttpSession session = request.getSession();

        session.setAttribute("listRoom", listRoom);
        session.setAttribute("numberRoom", listRoom.size());
        session.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());

        session.setAttribute("keyWorld", keyWorld);
        session.setAttribute("roomType", roomType);
        session.setAttribute("sortBy", sortBy);
        session.setAttribute("sort", sort);
        session.setAttribute("presentDeleted", viewDeleted);

        request.getRequestDispatcher("manageroom.jsp").forward(request, response);
    }

    private void addRoomType(HttpServletRequest request, HttpServletResponse response) {
        String typeName = request.getParameter("typeName");
        String description = request.getParameter("description");
        int numberPeople = Validation.parseStringToInt(request.getParameter("numberPeople"));
        String amenityIdsRaw = request.getParameter("amenityIds"); 
        String serviceIdsRaw = request.getParameter("serviceIds"); 

        RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
        roomTypeDAO.addNewRoomType(typeName, numberPeople, "", description);

        List<Integer> amenityIds = new ArrayList<>();
        if (amenityIdsRaw != null && !amenityIdsRaw.isEmpty()) {
            for (String idStr : amenityIdsRaw.split(",")) {
                amenityIds.add(Validation.parseStringToInt(idStr));
            }
        }
        List<Integer> serviceIds = new ArrayList<>();
        if (serviceIdsRaw != null && !serviceIdsRaw.isEmpty()) {
            for (String idStr : serviceIdsRaw.split(",")) {
                serviceIds.add(Validation.parseStringToInt(idStr));
            }
        }

        int tmpId = roomTypeDAO.getLatestRoomTypeId();
        roomTypeDAO.addAmenitiesToRoomType(tmpId, amenityIds);
        roomTypeDAO.addServicesToRoomType(tmpId, serviceIds);
        Map<String, List<Amenity>> grouped = new RoomTypeDAO().getAmenitiesGroupedByCategoryByRoomType(tmpId);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Amenity>> entry : grouped.entrySet()) {
            sb.append("+ ").append(entry.getKey()).append(": ");

            List<Amenity> amenities = entry.getValue();
            for (int i = 0; i < amenities.size(); i++) {
                sb.append(amenities.get(i).getName());
                if (i < amenities.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        new dao.RoomTypeDAO().updateRoomType(new RoomType(tmpId, typeName, description, "", numberPeople, sb.toString()));
    }

    private void editRoomType(HttpServletRequest request, HttpServletResponse response) {
        String typeIdRaw = request.getParameter("roomTypeID");
        String typeName = request.getParameter("typeName");
        String description = request.getParameter("description");
        String numberPeopleRaw = request.getParameter("numberPeople");
        String amenity = request.getParameter("amenity");
        String amenityIdsRaw = request.getParameter("amenityIds");
        String serviceIdsRaw = request.getParameter("serviceIds");

        int numberPeople = Validation.parseStringToInt(numberPeopleRaw);
        int typeId = Validation.parseStringToInt(typeIdRaw);

        new dao.RoomTypeDAO().deleteAmenitiesByRoomType(typeId);
        new dao.RoomTypeDAO().deleteServicesByRoomType(typeId);

        List<Integer> amenityIds = new ArrayList<>();
        if (amenityIdsRaw != null && !amenityIdsRaw.isEmpty()) {
            for (String idStr : amenityIdsRaw.split(",")) {
                amenityIds.add(Validation.parseStringToInt(idStr));
            }
        }
        
        List<Integer> serviceIds = new ArrayList<>();
        if (serviceIdsRaw != null && !serviceIdsRaw.isEmpty()) {
            for (String idStr : serviceIdsRaw.split(",")) {
                serviceIds.add(Validation.parseStringToInt(idStr));
            }
        }

        new dao.RoomTypeDAO().updateRoomType(new RoomType(typeId, typeName, description, "", numberPeople, ""));
        new dao.RoomTypeDAO().addAmenitiesToRoomType(typeId, amenityIds);
        new dao.RoomTypeDAO().addServicesToRoomType(typeId, serviceIds);

        Map<String, List<Amenity>> grouped = new RoomTypeDAO().getAmenitiesGroupedByCategoryByRoomType(typeId);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Amenity>> entry : grouped.entrySet()) {
            sb.append("+ ").append(entry.getKey()).append(": ");

            List<Amenity> amenities = entry.getValue();
            for (int i = 0; i < amenities.size(); i++) {
                sb.append(amenities.get(i).getName());
                if (i < amenities.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }

        new dao.RoomTypeDAO().updateRoomType(new RoomType(typeId, typeName, description, "", numberPeople, sb.toString()));
    }

    private void getRoomImg(HttpServletRequest request, HttpServletResponse response) {
        String roomIdRaw = request.getParameter("roomId");
        int roomId = Validation.parseStringToInt(roomIdRaw);
        List<RoomImage> listImg = new dao.RoomImageDAO().getListRoomImgByDetailID(roomId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(new Gson().toJson(listImg));
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkRoomNumber(HttpServletRequest request, HttpServletResponse response) {
        String roomNumber = request.getParameter("roomNumber");
        RoomDAO dao = new RoomDAO();
        boolean exists = dao.checkRoomNumberIsExist(roomNumber);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write("{\"exists\": " + exists + "}");
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    private void checkRoomTypeName(HttpServletRequest request, HttpServletResponse response) {
//        String roomNumber = request.getParameter("typeName");
//        String roomTypeID = request.getParameter("roomTypeID");
//        boolean exists = new dao.RoomTypeDAO().isRoomTypeNameExists(roomNumber,Validation.parseStringToInt(roomTypeID));
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            response.getWriter().write("{\"exists\": " + exists + "}");
//            System.out.println("{\"exists\": " + exists + "}");
//        } catch (IOException ex) {
//            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void getAllAmenity(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, List<Amenity>> groupedAmenities = new dao.RoomTypeDAO().getAmenitiesGroupedByCategory();

        // Chuyển map thành JSON
        Gson gson = new Gson();
        String json = gson.toJson(groupedAmenities);

        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkBookingStatus(HttpServletRequest request, HttpServletResponse response) {
        String[] roomIdParams = request.getParameterValues("roomIds");
        List<Integer> bookedRoomIds = new ArrayList<>();

        if (roomIdParams != null) {
            for (String idStr : roomIdParams) {
                try {
                    int roomId = Validation.parseStringToInt(idStr);
                    if (new dao.RoomDAO().isRoomBooked(roomId)) {
                        bookedRoomIds.add(roomId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONObject jsonResponse = new JSONObject();
        if (bookedRoomIds.isEmpty()) {
            jsonResponse.put("status", "ok");
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("bookedRoomIds", bookedRoomIds);
        }

        response.setContentType("application/json");
        try {
            response.getWriter().write(jsonResponse.toString());
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteRoomType(HttpServletRequest request, HttpServletResponse response) {
        String roomTypeID = request.getParameter("roomTypeID");

        RoomTypeDAO dao = new RoomTypeDAO();
        int typeId = Validation.parseStringToInt(roomTypeID);
        RoomType type = dao.getRoomTypeById(typeId);
        boolean check = dao.deleteRoomType(typeId);
        try {
            if (check) {
                response.getWriter().write("{\"success\": true, \"message\": \"Xóa thành công phòng " + type.getTypeName() + ".\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Xóa thất bại. Có thể RoomType " + type.getTypeName() + " đang được dùng.\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAllService(HttpServletRequest request, HttpServletResponse response) {
        List<Service> services = new dao.ServiceDAO().getListService(); 

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson(); 
        String json = gson.toJson(services);

        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(RoomCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
