/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ServiceDAO;
import entity.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Paths;

/**
 *
 * @author viet7
 */
@WebServlet(name = "ServiceUpdateServlet", urlPatterns = {"/serviceUpdate"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class ServiceUpdateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceUpdateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceUpdateServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        boolean status = "true".equals(request.getParameter("status"));

        Part filePart = request.getPart("imageFile");
        String imageURL = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            imageURL = "uploads/" + fileName;
        }

        Service service = new Service();
        service.setName(name);
        service.setCategory(category);
        service.setDescription(description);
        service.setPrice(price);
        service.setStatus(status);

        ServiceDAO dao = new ServiceDAO();

        if ("update".equals(request.getParameter("action"))) {
            int id = Integer.parseInt(request.getParameter("serviceID"));
            service.setServiceId(id);

            if (imageURL != null) {
                service.setImageUrl(imageURL);
            } else {
                Service old = new ServiceDAO().getServiceById(id);
                service.setImageUrl(old.getImageUrl());
            }

            if (dao.updateService(service)) {
                request.getSession().setAttribute("successMessage", "Cập nhật service thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
            }
        } else {
            if (imageURL != null) {
                service.setImageUrl(imageURL);
            } else {
                service.setImageUrl("");
            }
            if (dao.insertService(service)) {
                request.getSession().setAttribute("successMessage", "Cập nhật service thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
            }
        }
        response.sendRedirect("serviceList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
