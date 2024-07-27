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

@WebServlet("/deleteurl")
public class DeleteBook extends HttpServlet {
    private final static String Query = "delete from book_data where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        int id = Integer.parseInt(req.getParameter("id"));

        out.println("<html><head><title>Delete Book</title>");
        out.println("<style>");
        out.println("body { font-family: 'Roboto', sans-serif; background-color: #f8f9fa; margin: 0; padding: 20px; }");
        out.println("h1 { text-align: center; color: #343a40; margin-bottom: 20px; }");
        out.println(".container { max-width: 700px; margin: auto; padding: 20px; background: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("a { display: inline-block; padding: 12px 24px; margin-top: 20px; background: #007bff; color: white; text-align: center; text-decoration: none; border-radius: 4px; transition: background 0.3s; }");
        out.println("a:hover { background: #0056b3; }");
        out.println(".message { text-align: center; padding: 15px; border-radius: 4px; font-size: 18px; margin-bottom: 20px; }");
        out.println(".success { background-color: #d4edda; color: #155724; }");
        out.println(".error { background-color: #f8d7da; color: #721c24; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='container'>");
        out.println("<h1>Delete Book</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "khan");
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setInt(1, id);

            int count = ps.executeUpdate();
            if (count == 1) {
                out.println("<div class='message success'><h1>Record Deleted Successfully</h1></div>");
            } else {
                out.println("<div class='message error'><h1>Record Not Deleted</h1></div>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("<div class='message error'><h1>" + e.getMessage() + "</h1></div>");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<div class='message error'><h1>" + e.getMessage() + "</h1></div>");
        }

        out.println("<a href='home.html'>Back to Home</a>");
        out.println("<a href='booklist'>View Book List</a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
