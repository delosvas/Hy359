function post_login(){
    console.log("11")

    $('#check_login').text("Attempting to login...");

    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#check_login').text("Successfully logged in");

            setTimeout(function () {
                window.location.href = 'information_user.html';
            }, 2000);
        }else if (xhr.status === 403) {
            console.log("ok");
            $('#check_login').text("Invalid credentials");
        } else {
            console.log("ok2")
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'login_user',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}
