package in.sp.controllers;

import in.sp.database.DBConnection;
import in.sp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/loginform")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");


        String myemail1 = req.getParameter("email1");
        String mypass1 = req.getParameter("pass1");

        try {
            Connection con = DBConnection.getConnection();
            String select_sql_query = "SELECT * from register where email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(select_sql_query);
            ps.setString(1, myemail1);
            ps.setString(2, mypass1);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setCity(resultSet.getString("city"));

                HttpSession session_user = req.getSession();
                session_user.setAttribute("session_user",user);

               RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
               rd.forward(req,resp);
            } else {
                out.println("<h3 style = 'color:red'>Email and password didn't match</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("./login.html");
                rd.include(req, resp);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
