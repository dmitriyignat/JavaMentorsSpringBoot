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
        // }).done(function (data, textStatus, jqXHR) {
        //     var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
        //     window.location = preLoginInfo.url;
        // }).fail(function (jqXHR, textStatus, errorThrown) {
        //     alert('Booh! Wrong credentials, try again!');
        // });
    });
});