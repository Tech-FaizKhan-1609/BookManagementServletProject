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

@WebServlet("/register")
public class RegisterBook extends HttpServlet {
    private final static String Query = "Insert into book_data(bookName, BookEdition, BookPrice) Values(?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<html><head><title>Book Registration</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
        out.println("h1 { text-align: center; color: #333; }");
        out.println(".container { max-width: 500px; margin: auto; padding: 20px; background: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border-radius: 8px; }");
        out.println(".message { text-align: center; margin-bottom: 20px; }");
        out.println(".buttons { display: flex; justify-content: center; gap: 10px; }");
        out.println("a { display: inline-block; padding: 10px 20px; background: #4CAF50; color: white; text-align: center; text-decoration: none; border-radius: 4px; transition: background 0.3s; }");
        out.println("a:hover { background: #45a049; }");
        out.println("</style>");
        out.println("</head><body>");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "khan");
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            int count = ps.executeUpdate();

            out.println("<div class='container'>");
            if (count == 1) {
                out.print("<h1 class='message'>RECORD IS REGISTERED SUCCESSFULLY</h1>");
            } else {
                out.print("<h1 class='message'>RECORD NOT REGISTERED SUCCESSFULLY</h1>");
            }
            out.println("<div class='buttons'>");
            out.println("<a href='booklist'>View Book List</a>");
            out.println("<a href='home.html'>Home</a>");
            out.println("</div>");
            out.println("</div>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<div class='container'><h1 class='message'>" + e.getMessage() + "</h1></div>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<div class='container'><h1 class='message'>" + e.getMessage() + "</h1></div>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
