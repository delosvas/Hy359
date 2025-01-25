package servlets;

import database.tables.EditUsersTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "info_user", value = "/info_user")
public class info_user extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditUsersTable editUser=new EditUsersTable();
        String Json;
        HttpSession session=request.getSession();
        if(session.getAttribute("loggedIn")!=null){
            try {
                Json=editUser.databaseUserToJSON((session.getAttribute("loggedIn").toString()),(session.getAttribute("password").toString()));
            } catch (SQLException | ClassNotFoundException e) {
                response.setStatus(403);
                throw new RuntimeException(e);
            }
            System.out.println(Json);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(Json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}