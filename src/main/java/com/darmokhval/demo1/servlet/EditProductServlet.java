package com.darmokhval.demo1.servlet;

import com.darmokhval.demo1.entity.Product;
import com.darmokhval.demo1.service.DBUtils;
import com.darmokhval.demo1.service.MyUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ServiceConfigurationError;

@WebServlet(urlPatterns = {"/editProduct"})
public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public EditProductServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Connection connection = MyUtils.getStoredConnection(request);
        String code = (String) request.getParameter("code");
        Product product = null;
        String errorString = null;
        try {
            product = DBUtils.findProduct(connection, code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if(errorString != null && product == null) {
            response.sendRedirect(request.getServletPath() + "/productList");
            return;
        }
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", product);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
        dispatcher.forward(request, response);
    }
//  after user edit product and submited next method will be executed;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Connection connection = MyUtils.getStoredConnection(request);
        String code = (String) request.getParameter("code");
        String name = (String) request.getParameter("name");
        String priceStr = (String) request.getParameter("price");
        float price = 0;
        try {
            price = Float.parseFloat(priceStr);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Product product = new Product(code, name, price);
        String errorString = null;
        try {
            DBUtils.updateProduct(connection, product);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", product);
        if(errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }
    }




























}
