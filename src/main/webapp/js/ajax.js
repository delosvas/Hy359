
function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

function createTableFromJSONString(jsonString) {
    // Parse the JSON string into an object
    let data;
    try {
        data = JSON.parse(jsonString);
    } catch (error) {
        return "<p>Invalid JSON string provided.</p>";
    }

    // Start the HTML table
    let html = "<table border='1' style='border-collapse: collapse; width: 100%;'>";
    html += "<tr><th>Category</th><th>Value</th></tr>";

    // Loop through the object to add rows
    for (const key in data) {
        if (data.hasOwnProperty(key)) {
            html += <tr><td>${key}</td><td>${data[key]}</td></tr>;
        }
    }

    // Close the table
    html += "</table>";
    return html;
}

function getUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
          //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("User not exists or incorrect password");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetUser?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


function initDB() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              $("#ajaxContent").html("Successful Initialization");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Error Occured");
        }
    };

    xhr.open('GET', 'InitDB');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function deleteDB() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              $("#ajaxContent").html("Successful Deletion");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Error Occured");
        }
    };

    xhr.open('GET', 'DeleteDB');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


