package com.darmokhval.demo1.filter;

import com.darmokhval.demo1.entity.UserAccount;
import com.darmokhval.demo1.service.DBUtils;
import com.darmokhval.demo1.service.MyUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {
    public CookieFilter() {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        UserAccount userSession = MyUtils.getLoginedUser(session);

        if(userSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }
//      Connection створений в JDBCFilter;
        Connection connection = MyUtils.getStoredConnection(request);
//        Flag for Cookie checking;
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if(checked == null && connection != null) {
            String userName = MyUtils.getUserNameInCookie(req);
            try {
                UserAccount user = DBUtils.findUser(connection, userName);
                MyUtils.storeLoginedUser(session, user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Cancel checked Cookie;
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }
        chain.doFilter(request, response);
    }

}
