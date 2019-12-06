var roleName = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
document.getElementById("navUserPage").setAttribute("href", roleName + "/welcome");

$(document).ready(function ($) {
    var path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/')) + "/getUser";
    $.ajax({
        timeout: 1000,
        type: 'GET',
        url: path,
        success: function (data) {
            var message = "Hello " + data.user.login + " " + data.user.name;
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#welcome').html(message);

            console.log("SUCCESS : ", data);
            console.log(json)

        }
    });
});