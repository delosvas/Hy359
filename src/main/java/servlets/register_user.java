package servlets;

import database.init.JSON_Converter;
import database.tables.CheckForDuplicatesExample;
import database.tables.EditUsersTable;
import mainClasses.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "register_user", value = "/register_user")
public class register_user extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize the required classes
        JSON_Converter jsonConverter = new JSON_Converter();
        EditUsersTable editUsersTable = new EditUsersTable();
        CheckForDuplicatesExample checkForDuplicates = new CheckForDuplicatesExample();

        // Convert the JSON string to a User object
        String jsonString = jsonConverter.getJSONFromAjax(request.getReader());
        User user = editUsersTable.jsonToUser(jsonString);

        try {
            // Check for duplicate username
            if (!checkForDuplicates.isUserNameAvailable(user.getUsername())) {
                response.setStatus(403);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                return;
            }

            // Check for duplicate email
            if (!checkForDuplicates.isUserEmailAvailable(user.getEmail())) {
                response.setStatus(403);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                return;
            }

            // Check for duplicate telephone
            if (!checkForDuplicates.isUserTelephoneAvailable(user.getTelephone())) {
                response.setStatus(403);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                return;
            }

            // Convert the User object back to JSON string and print it
            String userJson = editUsersTable.userToJSON(user);
            System.out.println(userJson);

            // Add the user to the database
            editUsersTable.addUserFromJSON(userJson);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Set response status to 200 OK
        response.setStatus(200);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not implemented
    }
}