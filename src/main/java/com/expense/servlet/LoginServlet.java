package com.expense.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.expense.dao.DBConnection;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession httpSession = req.getSession();
        try(
        	Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
        	) {
        	ps.setString(1, email);
        	ps.setString(2, password);
        	try(ResultSet rs =ps.executeQuery();)
        	{
        		
        	
            if (rs.next()) {
            	int id = rs.getInt("id");
            	String name = rs.getString("name");
            	
            	httpSession.setAttribute("id", id);
            	httpSession.setAttribute("name", name);
            	httpSession.setAttribute("email", email);
            	httpSession.setAttribute("password", password);
            	
            	RequestDispatcher dispatcher=req.getRequestDispatcher("homepage");
            	dispatcher.forward(req, res);
            } else {
                res.getWriter().println("<h1>Invalid Login..Please Try Again...</h1>");
                RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
                dispatcher.include(req, res);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

