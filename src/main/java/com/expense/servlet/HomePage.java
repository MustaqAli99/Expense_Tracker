package com.expense.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/homepage")
public class HomePage extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession httpSession = req.getSession();
		String name=(String)httpSession.getAttribute("name");
		PrintWriter out=resp.getWriter();
		out.println("<h1> Hello "+name+" </h1>");
		out.println("<html>");
		out.println("<body>");
		out.println("<a href='addExpense.html'><button>Add Expense</button></a>");
		out.println("</body>");
		out.println("</html>");
	}
}
