package in.sp.controllers;

import in.sp.database.DBConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/regForm")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");


        String myName = req.getParameter("name1");
        String myEmail = req.getParameter("email1");
        String myPass = req.getParameter("pass1");
        String myCity = req.getParameter("city1");

        try {
            Connection con = DBConnection.getConnection();
            String insert_sql_query = "INSERT INTO register values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(insert_sql_query);
            ps.setString(1, myName);
            ps.setString(2, myEmail);
            ps.setString(3, myPass);
            ps.setString(4, myCity);

            int count = ps.executeUpdate();
            if (count > 0) {
                out.println("<h3 style = 'color:green'>register successfully</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("./login.html");
                rd.include(req, resp);
            } else {
                out.println("<h3 style = 'color:red'>register successfully</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("./registration.html");
                rd.include(req, resp);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}