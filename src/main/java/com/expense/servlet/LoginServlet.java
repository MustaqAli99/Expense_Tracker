package com.expense.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.expense.dao.DBConnection;

public class LoginServlet extends HttpServlet {
	static int id;
	public static int getId()
	{
		return id;
	}
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE email=? AND password=?"
            );
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	this.id = rs.getInt("id");
            	PrintWriter out = res.getWriter();
                out.println("<h1>Login Successful..</h1>");
                res.sendRedirect("addExpense.html");
            } else {
                res.getWriter().println("<h1>Invalid Login..Please Try Again...</h1>");
                RequestDispatcher dispatcher = req.getRequestDispatcher("LoginServlet");
                dispatcher.include(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

