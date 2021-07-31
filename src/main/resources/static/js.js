$(document).ready(function () {
    showAllUsers();
});

function listRoles(user) {
    let role;
    let roleList = '';
    for (let i = 0; i < user.roles.length; i++) {
        role = user.roles[i].role.substring(5);
        roleList += role + " ";
    }
    return roleList;
}

//-----------SHOW ALL Users------------
function showAllUsers() {
    $('#userTable tbody').empty();
    fetch('http://localhost:8088/api/users')
        .then(response => response.json())
        .then(users => {
            users.forEach(function (user) {
                let userRow = `$(<tr>
                        <th>${user.id}</th>
                        <td>${user.name}</td>
                        <td>${user.surName}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td>${listRoles(user)}</td>
                        <td>
                        <button type="button" onclick="getModalEdit(${user.id})" class="btn btn-info btn-sm">Edit</button>   
                        </td>
                        <td>
                        <button type="button" onclick="getModalDelete(${user.id})" class="btn btn-danger btn-sm">Delete</button>
                        </td>
                    </tr>)`;
                $('#userTable tbody').append(userRow);
            });
        });
}


const
    password = document.getElementById('password'),
    name = document.getElementById('name'),
    surName = document.getElementById('surName'),
    age = document.getElementById('age'),
    username = document.getElementById('username'),
    roles = document.getElementById('select'),
    id = document.getElementById('id');

//-----ADD NEW USER-----------------------------------
function addNewUser() {
    let selectedRoles = "";
    let roleList = "";
    for (var i = 0; i < roles.length; i++) {
        var role = roles.options[i];
        if (role.selected) {
            selectedRoles = selectedRoles.concat(role.value + (i != (roles.length - 1) ? "," : ""));
            roleList += role.value + " ";
        }
    }

    fetch('http://localhost:8088/api/newUser', {
        method: 'POST',
        body: JSON.stringify({
            password: password.value,
            name: name.value,
            surName: surName.value,
            age: age.value,
            username: username.value,
            roles: selectedRoles
        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    }).then(response => response.json())
        .then(user => {
            $('#tBody tr:last').after('<tr id=' + user.id + '>' +
                '<td>' + user.id + '</td>' +
                '<td>' + name.value + '</td>' +
                '<td>' + surName.value + '</td>' +
                '<td>' + age.value + '</td>' +
                '<td>' + username.value + '</td>' +
                '<td>' + selectedRoles + '</td>' +
                '</tr>');


            password.value = "";
            name.value = "";
            surName.value = "";
            age.value = "";
            username.value = "";
            roles.value = "";
        });
}

//-----DELETE USER-----------------------------------

function getModalDelete(id) {

    fetch('http://localhost:8088/api/users/' + id)
        .then(response => response.json())
        .then(user => {

            let modal = document.getElementById('modalWindow');

            modal.innerHTML =
                '<div id="modalDelete" ' +
                '     class="modal fade" tabindex="-1" role="dialog"' +
                '     aria-labelledby="TitleModalLabel" aria-hidden="true" ' +
                '     data-backdrop="static" data-keyboard="false">' +
                '    <div class="modal-dialog modal-dialog-scrollable">' +
                '        <div class="modal-content">' +
                '            <div class="modal-header">' +
                '                <h5 class="modal-title" id="TitleModalLabel">Delete user</h5>' +
                '                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                '                </button>' +
                '            </div>' +
                '            <div class="modal-body bg-white">' +
                '                <form id="formEditUser" style="width: 200px;" ' +
                '                       class="form-signin mx-auto font-weight-bold text-center">' +
                '                    <p>' +
                '                        <label>ID</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               name="id" value="' + user.id + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>First name</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               value="' + user.name + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Last name</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               value="' + user.surName + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Age</label>' +
                '                        <input class="form-control form-control-sm" type="number"' +
                '                               value="' + user.age + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Email</label>' +
                '                        <input class="form-control form-control-sm" type="email"' +
                '                               value="' + user.username + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Role</label>' +
                '                        <select class="form-control form-control-sm" multiple size="2" readonly>' +
                '                            <option value="ROLE_ADMIN"' + ' >ADMIN</option>' +
                '                            <option value="ROLE_USER"' +  '>USER</option>' +
                '                        </select>' +
                '                    </p>' +
                '                </form>' +
                '            </div>' +
                '            <div class="modal-footer">' +
                '                <button type="button" class="btn btn-secondary"' +
                '                        data-bs-dismiss="modal">Close</button>' +
                '                <button class="btn btn-danger" data-bs-dismiss="modal"' +
                '                        onclick="deleteUser(' + user.id + ')">Delete</button>' +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '</div>';

            $("#modalDelete").modal('show');

        });
}

function deleteUser(id) {
    fetch('http://localhost:8088/api/users/' + id, {
        method: 'DELETE',
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            $('#' + id).remove();
        });
}

//--------------EDIT USER-----------
function getModalEdit(id) {

    fetch('http://localhost:8088/api/users/' + id)
        .then(response => response.json())
        .then(user => {


            let modal = document.getElementById('modalWindow');

            modal.innerHTML =
                '<div id="modalEdit"' +
                '     class="modal fade" tabindex="-1" role="dialog"' +
                '     aria-labelledby="TitleModalLabel" aria-hidden="true"' +
                '     data-backdrop="static" data-keyboard="false">' +
                '    <div class="modal-dialog modal-dialog-scrollable">' +
                '        <div class="modal-content">' +
                '            <div class="modal-header">' +
                '                <h5 class="modal-title" id="TitleModalLabel">Edit user</h5>' +
                '                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>' +
                '                </button>' +
                '            </div>' +
                '            <div class="modal-body bg-white">' +
                '                <form id="formEditUser" style="width: 200px;"' +
                '                       class="form-signin mx-auto font-weight-bold text-center">' +
                '                    <p>' +
                '                        <label>ID</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               id="editID" name="id" value="' + user.id + '" readonly>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>First name</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               id="editName" value="' + user.name + '"' +
                '                               placeholder="First name" required>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Last name</label>' +
                '                        <input class="form-control form-control-sm" type="text"' +
                '                               id="editsurName" value="' + user.surName + '" ' +
                '                               placeholder="Last name" required>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Age</label>' +
                '                        <input class="form-control form-control-sm" type="number"' +
                '                               id="editAge" value="' + user.age + '" ' +
                '                               placeholder="Age" required>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Email</label>' +
                '                        <input class="form-control form-control-sm" type="email"' +
                '                               id="editUsername" value="' + user.username + '"' +
                '                               placeholder="Email" required>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Password</label>' +
                '                        <input class="form-control form-control-sm" type="email"' +
                '                               id="editPassword" value="' + user.password + '"' +
                '                               placeholder="Password" required>' +
                '                    </p>' +
                '                    <p>' +
                '                        <label>Role</label>' +
                '                        <select id="editRoles" name="roles" multiple size="2" required ' +
                '                               class="form-control form-control-sm">' +
                '                            <option value="ROLE_ADMIN"' + '>ADMIN</option>' +
                '                            <option value="ROLE_USER"' + '>USER</option>' +
                '                        </select>' +
                '                    </p>' +
                '                </form>' +
                '            </div>' +
                '            <div class="modal-footer">' +
                '                <button type="button" class="btn btn-secondary"' +
                '                        data-bs-dismiss="modal">Close</button>' +
                '                <button class="btn btn-primary" data-bs-dismiss="modal"' +
                '                        onclick="editUser()">Edit</button>' +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '</div>';

            $("#modalEdit").modal('show');

        });
}

function editUser() {

    let rolesEdit = window.formEditUser.editRoles;
    let selectedRolesEdit = "";
    let roleList = "";
    for (var i = 0; i < rolesEdit.length; i++) {
        var role = rolesEdit.options[i];
        if (role.selected) {
            selectedRolesEdit = selectedRolesEdit.concat(role.value + (i != (rolesEdit.length - 1) ? "," : ""));
            roleList += role.value + " ";
        }
    }
    let passwordEdit = window.formEditUser.editPassword,
        nameEdit = window.formEditUser.nameEdit,
        editsurName = window.formEditUser.editsurName,
        ageEdit = window.formEditUser.editAge,
        editUsername = window.formEditUser.editUsername;

    let id = window.formEditUser.editID.value;
    console.log(id);
    let arr = [{"id": 1, "role": "ROLE_ADMIN"}, {"id": 2, "role": "ROLE_USER"}]

    fetch('http://localhost:8088/api/users', {
        method: 'PUT',
        body: JSON.stringify({
            id: id,

            password: passwordEdit.value,
            name: nameEdit.value,
            surName: editsurName.value,
            age: ageEdit.value,
            username: editUsername.value,
            roles: roleList
        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            $('#' + id).replaceWith('<tr id=' + id + '>' +
                '<td>' + id + '</td>' +
                '<td>' + name.value + '</td>' +
                '<td>' + surName.value + '</td>' +
                '<td>' + age.value + '</td>' +
                '<td>' + username.value + '</td>' +
                '<td>' + roleList + '</td>' +
                '</tr>');
        });
}