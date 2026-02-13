package com.expense.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.expense.dao.DBConnection;
public class AddExpenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String title = req.getParameter("title");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String date = req.getParameter("date");
        int id=LoginServlet.getId();
        String category = req.getParameter("category");
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO expenses(user_id,title,amount,date,category) VALUES(?,?,?,?,?)"
            );
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setDouble(3, amount);
            ps.setString(4, date);
            ps.setString(5, category);
            ps.executeUpdate();
            PrintWriter out = res.getWriter();
            out.println("<h1>Expense Added..</h1>");
            res.sendRedirect("addExpense.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

