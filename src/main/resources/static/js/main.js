var roles;
var roleName = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
document.getElementById("navUserPage").setAttribute("href", roleName + "/welcome");

$(document).on("click", "#nav-profile-tab", function () {
    $("#inputRole").empty();
    $.each(roles, function (index, role) {
        $("#inputRole").append($("<option>").val(role).text(role));
    });
});

$(document).on("click", "#addUserButton", function (event) {
    var data = {};
    var formData = $(".addUser").serializeArray();
    $.each(formData, function(index, value) {
        if (value.name === "roles") {
            data[value.name] = value.value.split(",");
        } else {
            data[value.name] = value.value;
        }
    });
    console.log(JSON.stringify(data));
    let json = JSON.stringify(data);
    fetch("/admin/addUser", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json",
        }
    }).finally(() => location.reload());
});

$(document).on("click", "#try", function () {
    $(document).getElementById("try")
});

$(document).on("click", "#showRoles", function (event) {
    //event.preventDefault();
    if (document.getElementById("updateRole").style.visibility === 'hidden') {
        document.getElementById("updateRole").style.visibility = 'visible';
    } else {
        document.getElementById("updateRole").style.visibility = 'hidden';
    }
    event.stopImmediatePropagation();
});

$(document).on("click", "#addUserRole", function (event) {
    //event.preventDefault();
    if (document.getElementById("inputRole").style.visibility === 'hidden') {
        document.getElementById("inputRole").style.visibility = 'visible';
    } else {
        document.getElementById("inputRole").style.visibility = 'hidden';
    }
    event.stopImmediatePropagation();
});

$(document).on("click", "#updateRole", function (event) { $("#showRoles").val($("#updateRole").val());});
$(document).on("click", "#inputRole", function (event) { $("#addUserRole").val($("#inputRole").val());});

$(document).on("click", ".edit", function () {
    var user= $(this).data('user');
    var rolesUser = $.map(user.roles, function (role) {
        return role.name;
    });
    document.getElementById("updateRole").style.visibility = 'hidden';

    var message = rolesUser.includes("ROLE_ADMIN") ? "Edit admin " : "Edit user ";
    $("#modalHead").text(message + user.login + " " + user.password);

    $("#updateId").val(user.id);
    $("#updateLogin").val(user.login);
    $("#updateName").val(user.name);
    $("#updatePassword").val(user.password);
    $("#updateRole").empty();
    $.each(roles, function (index, role) {
        $("#updateRole").append($("<option>").val(role).text(role).attr('selected', rolesUser.includes(role)));
    });
    $("#showRoles").val($("#updateRole").val());
});

$(document).on("click", "#deleteButton", function (event) {
    var id = $(this).data('id');
    console.log(id);
    $.ajax({
        type: 'POST',
        url: '/admin/delete',
        data: {id : id},
        timeout: 100,
        success:  function () {
            $("#tableBody").empty();
            readUsers();
        }
    });
});

$(document).on("click", "#submit", function (event) {
    event.preventDefault();
    var data = {};
    var formData = $(".update").serializeArray();
    $.each(formData, function(index, value) {
        if (value.name === "roles") {
            data[value.name] = value.value.split(",");
        } else {
            data[value.name] = value.value;
        }
    });
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        url: '/admin/updateUser',
        data: JSON.stringify(data),
        timeout: 100,
        success:  function () {
            location.reload()
        }
    });
});

$(document).ready(readUsers());

function readUsers() {
    $("#tableBody").empty();
    $.ajax({
        type: 'POST',
        url: '/admin/readUsers',
        timeout: 100,
        success: function (data) {
            roles = $.map(data.roles, function (role) {
                return role.name;
            });
            $.each(data.users, function(i, user) {
                $("#tableBody").append($('<tr>').append(
                    $('<th>').append($('<span>')).text(user.id),
                    $('<td>').append($.map(user.roles, function (role) {
                        var tt = document.createElement('p');
                        tt.className = "try";
                        tt.append(role.name);
                        return tt;
                    })),
                    $('<td>').append($('<span>')).text(user.login),
                    $('<td>').append($('<span>')).text(user.password),
                    $('<td>').append($('<span>')).text(user.name),
                    $('<td>').append($('<button>').text("Edit").attr({
                        "type" : "button",
                        "class" : "btn btn-primary edit",
                        "data-toggle" : "modal",
                        "data-target" : "#exampleModal"
                    })
                        .data("user", user)),
                    $('<td>').append($('<button>').text("Delete").attr({
                        "id" : "deleteButton",
                        "type" : "button",
                        "class" : "btn btn-danger",
                        "formaction" : "delete"
                    })
                        .data("id", user.id))
                    )
                );
            });
        }
    });
}

