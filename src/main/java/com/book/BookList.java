package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookList extends HttpServlet {
	private final static String Query = "Select * From book_data";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		out.println("<html><head><title>Book List</title>");
		out.println("<style>");
		out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
		out.println("h1 { text-align: center; color: #333; margin-bottom: 20px; }");
		out.println(
				".container { max-width: 800px; margin: auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
		out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
		out.println("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }");
		out.println("th { background-color: #4CAF50; color: white; }");
		out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
		out.println("tr:hover { background-color: #f5f5f5; }");
		out.println(
				"a { color: #4CAF50; text-decoration: none; padding: 5px 10px; border: 1px solid #4CAF50; border-radius: 5px; transition: background-color 0.3s, color 0.3s; }");
		out.println("a:hover { background-color: #4CAF50; color: white; }");
		out.println("</style>");
		out.println("</head><body>");

		out.println("<div class='container'>");
		out.println("<h1>Book List</h1>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "khan");
			PreparedStatement ps = connection.prepareStatement(Query);
			ResultSet rs = ps.executeQuery();

			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Book Id</th>");
			out.println("<th>Book Name</th>");
			out.println("<th>Book Edition</th>");
			out.println("<th>Book Price</th>");
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt(1) + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getString(3) + "</td>");
				out.println("<td>" + rs.getFloat(4) + "</td>");
				out.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>EDIT</a></td>");
				out.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>DELETE</a></td>");
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("<h1>" + e.getMessage() + "</h1>");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<h1>" + e.getMessage() + "</h1>");
		}

		out.println("<a href='home.html'>Home</a>");
		out.println("</div>");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
