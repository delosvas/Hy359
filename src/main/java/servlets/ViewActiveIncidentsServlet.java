package servlets;

import com.google.gson.Gson;
import database.tables.EditIncidentsTable;
import mainClasses.Incident;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ViewActiveIncidentsServlet", urlPatterns = {"/view_active_incidents"})
public class ViewActiveIncidentsServlet extends HttpServlet {
    private EditIncidentsTable editIncidentsTable;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        editIncidentsTable = new EditIncidentsTable();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Incident> activeIncidents = editIncidentsTable.getActiveIncidents();

            String json = gson.toJson(activeIncidents);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(json);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error retrieving incidents.");
            e.printStackTrace();
        }
    }
}
