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
@WebServlet("/addexpense")
public class AddExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String title = req.getParameter("title");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String date = req.getParameter("date");
        String category = req.getParameter("category");

        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("id");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO expenses(user_id,title,amount,date,category) VALUES(?,?,?,?,?)");
        	){

            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setDouble(3, amount);
            ps.setString(4, date);
            ps.setString(5, category);

            ps.executeUpdate();

            // Success page UI
            out.println("<html><head><title>Success</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#f4f6f9;text-align:center;padding:50px;}");
            out.println("button{padding:12px;margin:10px;background:#3498db;color:white;border:none;cursor:pointer;}");
            out.println("</style></head><body>");

            out.println("<h2> Expense Added Successfully!</h2>");

            out.println("<a href='addExpense.html'><button>Add Another</button></a>");

            out.println("<form action='homepage' method='post'>");
            out.println("<button>Go to Dashboard</button>");
            out.println("</form>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error adding expense</h3>");
        }
    }
}