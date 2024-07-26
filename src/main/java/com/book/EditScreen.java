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

@WebServlet("/editScreen")
public class EditScreen extends HttpServlet {
    private final static String Query = "Select * From book_data where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        int id = Integer.parseInt(req.getParameter("id"));

        out.println("<html><head><title>Edit Book</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
        out.println("h1 { text-align: center; color: #333; margin-bottom: 20px; }");
        out.println(".container { max-width: 800px; margin: auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("form { display: flex; flex-direction: column; }");
        out.println("label { margin-bottom: 10px; color: #333; font-weight: bold; }");
        out.println("input[type='text'], input[type='submit'], input[type='reset'] { padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }");
        out.println("input[type='submit'], input[type='reset'] { background-color: #4CAF50; color: white; border: none; cursor: pointer; transition: background-color 0.3s; }");
        out.println("input[type='submit']:hover, input[type='reset']:hover { background-color: #45a049; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
        out.println("td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }");
        out.println("a { display: inline-block; padding: 10px 20px; background: #4CAF50; color: white; text-align: center; text-decoration: none; border-radius: 4px; transition: background 0.3s; margin-top: 20px; }");
        out.println("a:hover { background: #45a049; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='container'>");
        out.println("<h1>Edit Book</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "khan");
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.println("<form action='editurl' method='post'>");
                out.println("<input type='hidden' name='id' value='" + id + "'>");
                out.println("<label for='bookName'>Book Name</label>");
                out.println("<input type='text' id='bookName' name='bookName' value='" + rs.getString(2) + "'>");
                out.println("<label for='bookEdition'>Book Edition</label>");
                out.println("<input type='text' id='bookEdition' name='bookEdition' value='" + rs.getString(3) + "'>");
                out.println("<label for='bookPrice'>Book Price</label>");
                out.println("<input type='text' id='bookPrice' name='bookPrice' value='" + rs.getFloat(4) + "'>");
                out.println("<div>");
                out.println("<input type='submit' value='Edit'>");
                out.println("<input type='reset' value='Cancel'>");
                out.println("</div>");
                out.println("</form>");
            } else {
                out.println("<h1>No Record Found</h1>");
            }
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
