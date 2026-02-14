package com.expense.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.expense.dao.DBConnection;

@WebServlet("/homepage")
public class HomePage extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("id");

        String date = req.getParameter("date");
        String category = req.getParameter("category");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        double total = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM expenses WHERE user_id=?";

            if (date != null && !date.isEmpty())
                sql += " AND date=?";

            if (category != null && !category.isEmpty())
                sql += " AND category=?";

            PreparedStatement ps = con.prepareStatement(sql);

            int index = 1;
            ps.setInt(index++, userId);

            if (date != null && !date.isEmpty())
                ps.setString(index++, date);

            if (category != null && !category.isEmpty())
                ps.setString(index++, category);

            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Expenses</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#f4f6f9;padding:20px;}");
            out.println("table{width:100%;border-collapse:collapse;background:white;}");
            out.println("th,td{padding:10px;border-bottom:1px solid #ddd;}");
            out.println("th{background:#3498db;color:white;}");
            out.println("</style></head><body>");

            out.println("<h2>Your Expenses</h2>");

            out.println("<table>");
            out.println("<tr><th>Title</th><th>Amount</th><th>Date</th><th>Category</th></tr>");

            while (rs.next()) {

                double amt = rs.getDouble("amount");
                total += amt;

                out.println("<tr>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + amt + "</td>");
                out.println("<td>" + rs.getString("date") + "</td>");
                out.println("<td>" + rs.getString("category") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<h3>Total Expense: " + total + "</h3>");

            out.println("<br><a href='homepage.html'><button>Back to Dashboard</button></a>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
