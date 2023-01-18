package com.darmokhval.demo1.servlet;

import com.darmokhval.demo1.entity.UserAccount;
import com.darmokhval.demo1.service.DBUtils;
import com.darmokhval.demo1.service.MyUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public LoginServlet(){

    }
//    Show login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
//        forward to login.jsp
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMeString = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeString);

        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;
        if(userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Password and username required";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
//                find user in the DB
                user = DBUtils.findUser(conn, userName, password);
                if(user == null) {
                    hasError = true;
                    errorString = "User Name or password is invalid";
                }
            } catch(SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
//        if error, forwards to login.jsp
        if(hasError) {
            user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
//            storing info in request attribute before forwarding
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
//            forward to login.jsp
            RequestDispatcher dispatcher = this.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        }
//        if no error - store info in session and redirect to userInfo.jsp
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
//            if remember me
            if(remember) {
                MyUtils.storeUserCookie(response, user);
            }
//            else delete cookie
            else {
                MyUtils.deleteUserCookie(response);
            }
//            redirect to userInfo page
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
    }
}
