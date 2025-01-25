package servlets;

import database.init.JSON_Converter;
import database.tables.CheckForDuplicatesExample;
import database.tables.EditVolunteersTable;
import mainClasses.Volunteer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "register_volunteer", value = "/register_volunteer")
public class register_volunteer extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize the required classes
        JSON_Converter jsonConverter = new JSON_Converter();
        EditVolunteersTable editVolunteersTable = new EditVolunteersTable();
        CheckForDuplicatesExample checkForDuplicates = new CheckForDuplicatesExample();

        // Convert the JSON string to a Volunteer object
        String jsonString = jsonConverter.getJSONFromAjax(request.getReader());
        Volunteer volunteer = editVolunteersTable.jsonToVolunteer(jsonString);

        try {
            // Check for duplicate username
            if (!checkForDuplicates.isUserNameAvailable(volunteer.getUsername())) {
                response.setStatus(403);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                return;
            }

            // Convert the Volunteer object back to JSON string and print it
            String volunteerJson = editVolunteersTable.volunteerToJSON(volunteer);
            System.out.println(volunteerJson);

            // Add the volunteer to the database
            editVolunteersTable.addVolunteerFromJSON(volunteerJson);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not implemented
    }
}