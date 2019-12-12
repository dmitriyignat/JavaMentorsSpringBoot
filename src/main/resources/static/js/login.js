$(document).ready(function ($) {
    $('#loginform').submit(function (event) {
        event.preventDefault();
        var data = 'app_username=' + $('#inputLogin').val() + '&app_password=' + $('#inputPassword').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/login'
        });
    });
});