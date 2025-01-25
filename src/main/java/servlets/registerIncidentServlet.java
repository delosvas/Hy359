package servlets;

import database.init.JSON_Converter;

import database.tables.CheckForDuplicatesExample;
import database.tables.EditIncidentsTable;

import mainClasses.Incident;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registerIncidentServlet", value = "/registerIncidentServlet")
public class registerIncidentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*JSON_Converter jsonConverter = new JSON_Converter();
        EditIncidentsTable editIncidentsTable = new EditIncidentsTable();


        // Convert the JSON string to a User object
        String jsonString = jsonConverter.getJSONFromAjax(request.getReader());
        Incident incident = editIncidentsTable.jsonToIncident(jsonString);

        *//*try {
            if(){
                return;
            }



            // Convert the User object back to JSON string and print it
            String incidentJson = editIncidentsTable.incidentToJSON(incident);
            System.out.println(incidentJson);
            // Add the user to the database
            editIncidentsTable.addIncidentFromJSON(incidentJson);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*//*

        // Set response status to 200 OK
        response.setStatus(200);*/




    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSON_Converter jsonConverter = new JSON_Converter();
        EditIncidentsTable editIncidentsTable = new EditIncidentsTable();

        String jsonString;

        // Ανάγνωση του JSON από το αίτημα
        try {
            jsonString = jsonConverter.getJSONFromAjax(request.getReader());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error reading JSON from request.");
            e.printStackTrace(); // Καταγραφή του σφάλματος
            return;
        }

        // Μετατροπή του JSON σε αντικείμενο Incident
        Incident incident;
        try {
            incident = editIncidentsTable.jsonToIncident(jsonString);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid JSON format.");
            e.printStackTrace(); // Καταγραφή του σφάλματος
            return;
        }

        // Καταχώρηση του συμβάντος στη βάση
        try {
            if(!(incident.getStatus().equals("Pending")||incident.getStatus() .equals( "Resolved")||incident.getStatus() .equals( "In Progress"))){
                response.setStatus(406);
                return;
            }
            // Προσθήκη του συμβάντος στη βάση
            editIncidentsTable.addIncidentFromJSON(jsonString);

            // Επιστροφή επιτυχίας
            response.setStatus(200);


            response.getWriter().write("Incident registered successfully.");
        } catch ( ClassNotFoundException e) {
            response.setStatus(406);

            e.printStackTrace(); // Καταγραφή του σφάλματος
        }
    }

}

