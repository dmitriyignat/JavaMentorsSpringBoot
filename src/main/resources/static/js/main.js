let roles;
let roleName = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
let data = {};
let formRoles;
let originalRoles;
let formData;
let json;
document.getElementById("navUserPage").setAttribute("href", roleName + "/welcome");

$(document).on("click", "#nav-profile-tab", function () {
    $("#inputRole").empty();
    $.each(roles, function (index, role) {
        $("#inputRole").append($("<option>").val(role.name).text(role.name));
    });
});

function fillData() {
    originalRoles = roles.map((role) => role)
    $.each(formData, function(index, value) {
        if (value.name === "roles") {
            formRoles = value.value.split(",");
            $.each(roles, function (j, role) {
                if (!formRoles.includes(role.name)) {
                    originalRoles.splice(originalRoles.indexOf(role), 1);
                }
                data[value.name] = originalRoles;
            })
        } else {
            data[value.name] = value.value;
        }
    });
}
$(document).on("click", "#addUserButton", function () {
    formData = $(".addUser").serializeArray();
    formData.pop();
    fillData();
    json = JSON.stringify(data);

    fetch("/admin/addUser", {
        method: "PUT",
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
    if (document.getElementById("updateRole").style.visibility === 'hidden') {
        document.getElementById("updateRole").style.visibility = 'visible';
    } else {
        document.getElementById("updateRole").style.visibility = 'hidden';
    }
    event.stopImmediatePropagation();
});

$(document).on("click", "#addUserRole", function (event) {
    if (document.getElementById("inputRole").style.visibility === 'hidden') {
        document.getElementById("inputRole").style.visibility = 'visible';
    } else {
        document.getElementById("inputRole").style.visibility = 'hidden';
    }
    event.stopImmediatePropagation();
});

$(document).on("click", "#updateRole", function () { $("#showRoles").val($("#updateRole").val());});
$(document).on("click", "#inputRole", function () { $("#addUserRole").val($("#inputRole").val());});

$(document).on("click", ".edit", function () {
    let user= $(this).data('user');
    let rolesUser = $.map(user.roles, function (role) {
        return role.name;
    });
    document.getElementById("updateRole").style.visibility = 'hidden';

    let message = rolesUser.includes("ROLE_ADMIN") ? "Edit admin " : "Edit user ";
    $("#modalHead").text(message + user.login + " " + user.password);
    let updateRole = $("#updateRole");

    $("#updateId").val(user.id);
    $("#updateLogin").val(user.login);
    $("#updateName").val(user.name);
    $("#updatePassword").val(user.password);
    updateRole.empty();
    $.each(roles, function (index, role) {
        updateRole.append($("<option>").val(role.name).text(role.name).attr('selected', rolesUser.includes(role.name)));
    });
    $("#showRoles").val(updateRole.val());
});

$(document).on("click", "#deleteButton", function () {
    let id = $(this).data('id');
    console.log(id);
    $.ajax({
        type: 'DELETE',
        url: '/admin/delete',
        data: {id : id},
        timeout: 100,
        success:  function () {
            $("#tableBody").empty();
            readUsers();
        }
    });
});

$(document).on("click", "#submit", function () {
    formData = $(".update").serializeArray();
    fillData();
    json = JSON.stringify(data);
    fetch("/admin/updateUser", {
        method: "POST",
        body: json,
        headers: {
            "Content-Type": "application/json",
        }
    }).finally(() => location.reload());
});

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/admin/roles',
        timeout: 3000,
        success: function (data) {
            roles = data;
        }
    })
});
$(document).ready(readUsers());

function readUsers() {
    $("#tableBody").empty();
    $.ajax({
        type: 'GET',
        url: '/admin/readUsers',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function(i, user) {
                $("#tableBody").append($('<tr>').append(
                    $('<th>').append($('<span>')).text(user.id),
                    $('<td>').append($.map(user.roles, function (role) {
                        let tagP = document.createElement('p');
                        tagP.className = "try";
                        tagP.append(role.name);
                        return tagP;
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

