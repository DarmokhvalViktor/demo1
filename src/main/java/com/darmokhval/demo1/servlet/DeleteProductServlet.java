package com.darmokhval.demo1.servlet;

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

@WebServlet(urlPatterns = {"/deleteProduct"})
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public DeleteProductServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Connection connection = MyUtils.getStoredConnection(request);
        String code = (String) request.getParameter("code");
        String errorString = null;
        try {
            DBUtils.deleteProduct(connection, code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        if(errorString != null) {
            request.setAttribute("erorrString", errorString);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }


















}
