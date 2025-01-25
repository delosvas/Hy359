document.addEventListener("DOMContentLoaded", function() {
    // Φόρτωση ενεργών περιστατικών κατά την αρχική φόρτωση της σελίδας
    loadActiveIncidents();
});

/**
 * Λειτουργία για την καταχώρηση ενός νέου περιστατικού
 */
function post_register_user() {
    const form = document.getElementById("registrationForm");
    const checkDiv = document.getElementById("check");

    // Συλλογή δεδομένων από τη φόρμα
    const formData = new FormData(form);
    const data = {};

    formData.forEach(function(value, key) {
        data[key] = value.trim();
    });

    // Δημιουργία JSON δεδομένων
    const jsonData = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "registerIncidentServlet", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Εμφάνιση μηνύματος επιτυχίας
                checkDiv.style.color = "green";
                checkDiv.textContent = "Επιτυχής Καταχώρηση!";
                form.reset();
                loadActiveIncidents();
            } else if (xhr.status === 406) {
                // Εμφάνιση μηνύματος σφάλματος λόγω μη αποδεκτών δεδομένων
                checkDiv.style.color = "red";
                checkDiv.textContent = "Μη αποδεκτά δεδομένα. Παρακαλώ έλεγξε τις εισαγωγές σου.";
            } else {
                // Εμφάνιση γενικού μηνύματος σφάλματος
                checkDiv.style.color = "red";
                checkDiv.textContent = "Σφάλμα κατά την καταχώρηση περιστατικού. Παρακαλώ δοκίμασε ξανά.";
            }
        }
    };

    xhr.onerror = function() {
        // Εμφάνιση μηνύματος σφάλματος δικτύου
        checkDiv.style.color = "red";
        checkDiv.textContent = "Σφάλμα δικτύου. Παρακαλώ δοκίμασε ξανά.";
    };

    xhr.send(jsonData);
}

/**
 * Λειτουργία για τη φόρτωση των ενεργών περιστατικών από τον server
 */
function loadActiveIncidents() {
    const activeIncidentsContainer = document.getElementById("activeIncidents");
    activeIncidentsContainer.innerHTML = "<p>Φόρτωση ενεργών περιστατικών...</p>";

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "view_active_incidents", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                try {
                    const incidents = JSON.parse(xhr.responseText);
                    displayActiveIncidents(incidents);
                } catch (error) {
                    activeIncidentsContainer.innerHTML = "<p>Σφάλμα επεξεργασίας των περιστατικών.</p>";
                    console.error("Error parsing JSON:", error);
                }
            } else {
                activeIncidentsContainer.innerHTML = "<p>Σφάλμα κατά τη φόρτωση των περιστατικών.</p>";
            }
        }
    };

    xhr.onerror = function() {
        activeIncidentsContainer.innerHTML = "<p>Σφάλμα δικτύου κατά τη φόρτωση των περιστατικών.</p>";
    };

    xhr.send();
}

/**
 * Λειτουργία για την εμφάνιση των ενεργών περιστατικών στο DOM
 * @param {Array} incidents - Ο πίνακας με τα ενεργά περιστατικά
 */
function displayActiveIncidents(incidents) {
    const activeIncidentsContainer = document.getElementById("activeIncidents");

    if (Array.isArray(incidents) && incidents.length > 0) {
        activeIncidentsContainer.innerHTML = "";
        incidents.forEach(function(incident) {
            const div = document.createElement("div");
            div.innerHTML = `
                <strong>Τύπος:</strong> ${incident.incident_type}<br>
                <strong>Περιγραφή:</strong> ${incident.description}<br>
                <strong>Διεύθυνση:</strong> ${incident.address}<br>
                <strong>Επίπεδο Κινδύνου:</strong> ${incident.danger}<br>
                <strong>Κατάσταση:</strong> ${incident.status}<br>
                <strong>Ημερομηνία Έναρξης:</strong> ${incident.start_datetime}<br>
            `;
            activeIncidentsContainer.appendChild(div);
        });
    } else {
        activeIncidentsContainer.innerHTML = "<p>Δεν υπάρχουν ενεργά περιστατικά αυτή τη στιγμή.</p>";
    }
}

/**
 * Λειτουργία επιστροφής στην προηγούμενη σελίδα
 */
function goBack() {
    window.history.back();
}
