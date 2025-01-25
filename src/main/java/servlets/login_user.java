package servlets;

import database.tables.EditUsersTable;
import mainClasses.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "login_user", value = "/login_user")
public class login_user extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        HttpSession session=request.getSession(true);
        EditUsersTable editUser=new EditUsersTable();
        User user=null;
        try {
            user= editUser.databaseToUsers(username,password);
            if(user!=null){
                session.setAttribute("loggedIn", username);
                session.setAttribute("password", password);
                response.setStatus(200);
            }else{
                System.out.println(403);
                response.setStatus(403);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}