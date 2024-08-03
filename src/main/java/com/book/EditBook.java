package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditBook extends HttpServlet {
    private final static String Query = "Update book_data set bookName=?, bookEdition=?, bookPrice=? where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String idParam = req.getParameter("id");
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookPriceParam = req.getParameter("bookPrice");

        if (idParam == null || bookName == null || bookEdition == null || bookPriceParam == null) {
            out.println("<html><head><title>Edit Book</title></head><body>");
            out.println("<h1>Invalid input. Please make sure all parameters are provided.</h1>");
            out.println("<a href='booklist'>Book List</a>");
            out.println("</body></html>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            float bookPrice = Float.parseFloat(bookPriceParam);

            out.println("<html><head><title>Edit Book</title>");
            out.println("<style>");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;font-variant:small-caps; background-color: #f0f4f8; margin: 0; padding: 20px; }");
            out.println("h1 { text-align: center;font-variant:small-caps; color: #333; margin-bottom: 20px; }");
            out.println(".container { max-width: 800px;font-variant:small-caps; margin: auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
            out.println("form { display: flex;font-variant:small-caps; flex-direction: column; gap: 15px; }");
            out.println("label { color: #333;font-variant:small-caps; font-weight: bold; font-size: 16px; }");
            out.println("input[type='text'] { padding: 12px;font-variant:small-caps; border: 1px solid #ddd; border-radius: 4px; font-size: 16px; }");
            out.println("input[type='submit'], input[type='reset'] { padding: 12px;font-variant:small-caps; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; transition: background-color 0.3s; }");
            out.println("input[type='submit'] { background-color: #4CAF50;font-variant:small-caps; }");
            out.println("input[type='submit']:hover { background-color: #45a049;font-variant:small-caps; }");
            out.println("input[type='reset'] { background-color: #f44336;}");
            out.println("input[type='reset']:hover { background-color: #e53935; }");
            out.println("a { display: inline-block; padding: 12px 20px;font-variant:small-caps; margin-top: 20px; background-color: #007bff; color: white; text-align: center; text-decoration: none; border-radius: 4px; transition: background-color 0.3s; }");
            out.println("a:hover { background-color: #0056b3; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class='container'>");
            out.println("<h1>Edit Book</h1>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "khan");
                PreparedStatement ps = connection.prepareStatement(Query);
                ps.setString(1, bookName);
                ps.setString(2, bookEdition);
                ps.setFloat(3, bookPrice);
                ps.setInt(4, id);
                int count = ps.executeUpdate();
                if (count == 1) {
                    out.println("<h2 style='text-align: center; color: #4CAF50;'>Record Updated Successfully</h2>");
                } else {
                    out.println("<h2 style='text-align: center; color: #f44336;'>Record Not Updated</h2>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.print("<h2 style='text-align: center; color: #f44336;'>" + e.getMessage() + "</h2>");
            } catch (Exception e) {
                e.printStackTrace();
                out.print("<h2 style='text-align: center; color: #f44336;'>" + e.getMessage() + "</h2>");
            }
            out.println("<a href='home.html'>Home</a>");
            out.println("<a href='booklist'>Book List</a>");
            out.println("</div>");
            out.println("</body></html>");

        } catch (NumberFormatException e) {
            out.println("<html><head><title>Edit Book</title></head><body>");
            out.println("<h1 style='text-align: center; color: #f44336;'>Invalid input. Please enter valid numbers for id and price.</h1>");
            out.println("<a href='booklist'>Book List</a>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
