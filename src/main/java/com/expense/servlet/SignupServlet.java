package com.expense.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.expense.dao.DBConnection;
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO users(name,email,password) VALUES(?,?,?)")) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            // ✅ ONLY redirect
            res.sendRedirect("login.html");

        } catch (Exception e) {
            e.printStackTrace();

            // fallback error UI
            res.setContentType("text/html");
            res.getWriter().println("<h2>Signup Failed</h2>");
            res.getWriter().println("<a href='signup.html'>Try Again</a>");
        }
    }
}

