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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String title = req.getParameter("title");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String date = req.getParameter("date");
        String category = req.getParameter("category");
        HttpSession httpSession=req.getSession();
        int id=(int) httpSession.getAttribute("id");
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
            out.println("<html>");
    		out.println("<body>");
    		out.println("<h1>Expense Added..</h1> <br>");
    		out.println("<a href='addExpense.html'><button>Add Another Expense</button></a> <br>");
    		out.println("<form action='homepage' method='post'> <button>Homepage</button></form>");
    		out.println("</body>");
    		out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

