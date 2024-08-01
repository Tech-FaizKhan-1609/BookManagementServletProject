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
public class EditScreenOfBook extends HttpServlet {
    private final static String Query = "Select * From book_data where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        int id = Integer.parseInt(req.getParameter("id"));

        out.println("<html><head><title>Edit Book</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #e9ecef; margin: 0; padding: 20px; }");
        out.println("h1 { text-align: center; color: #343a40; margin-bottom: 20px; }");
        out.println(".container { max-width: 700px; margin: auto; padding: 30px; background: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("form { display: flex; flex-direction: column; gap: 15px; }");
        out.println("label { color: #495057; font-weight: 600; font-size: 16px; }");
        out.println("input[type='text'] { padding: 12px; border: 1px solid #ced4da; border-radius: 4px; font-size: 16px; }");
        out.println("input[type='submit'], input[type='reset'] { padding: 12px; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; transition: background-color 0.3s; }");
        out.println("input[type='submit'] { background-color: #28a745; }");
        out.println("input[type='submit']:hover { background-color: #218838; }");
        out.println("input[type='reset'] { background-color: #dc3545; }");
        out.println("input[type='reset']:hover { background-color: #c82333; }");
        out.println("a { display: inline-block; padding: 12px 20px; margin-top: 20px; background-color: #007bff; color: white; text-align: center; text-decoration: none; border-radius: 4px; transition: background-color 0.3s; }");
        out.println("a:hover { background-color: #0056b3; }");
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
                out.println("<input type='text' id='bookName' name='bookName' value='" + rs.getString("bookName") + "' required>");
                out.println("<label for='bookEdition'>Book Edition</label>");
                out.println("<input type='text' id='bookEdition' name='bookEdition' value='" + rs.getString("bookEdition") + "' required>");
                out.println("<label for='bookPrice'>Book Price</label>");
                out.println("<input type='text' id='bookPrice' name='bookPrice' value='" + rs.getFloat("bookPrice") + "' required>");
                out.println("<div>");
                out.println("<input type='submit' value='Save Changes'>");
                out.println("<input type='reset' value='Reset'>");
                out.println("</div>");
                out.println("</form>");
            } else {
                out.println("<h2 style='text-align: center; color: #dc3545;'>No Record Found</h2>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("<h2 style='text-align: center; color: #dc3545;'>" + e.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h2 style='text-align: center; color: #dc3545;'>" + e.getMessage() + "</h2>");
        }

        out.println("<a href='home.html'>Back to Home</a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
