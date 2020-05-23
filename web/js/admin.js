//折叠菜单效果
$(function () {
    const Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        const links = this.el.find('.link');

        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    };

    Accordion.prototype.dropdown = function (e) {
        const $el = e.data.el;

        let $this = $(this), $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
    };

    const accordion = new Accordion($('#accordion'), false);
});

function queryRequest(object) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", "/student_management_system/AdminDao?action=query_all_" + object, true);
    xmlhttp.send();
}

function insertRequest(object) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    };
    let insert_url = "/student_management_system/AdminDao?action=insert_" + object;
    if (object === "user") {
        // Insert User
        const user = document.getElementsByClassName("user_insert_input");
        const username = user[0].value.toString();
        const password = user[1].value.toString();
        const usertype = user[2].value.toString();
        const foreignid = user[3].value.toString();
        insert_url = insert_url + "&username=" + username + "&password=" + password + "&usertype=" + usertype + "&foreignid=" + foreignid;
    } else if (object === "campus") {
        // Insert Campus
        const campus = document.getElementsByClassName("campus_insert_input");
        const id = campus[0].value.toString();
        const name = campus[1].value.toString();
        const address = campus[2].value.toString();
        insert_url = insert_url + "&id=" + id + "&name=" + name + "&address=" + address;
    } else if (object === "department") {
        // Insert Department
        const department = document.getElementsByClassName("department_insert_input");
        const id = department[0].value.toString();
        const name = department[1].value.toString();
        const address = department[2].value.toString();
        const dean = department[3].value.toString();
        const campus_id = department[4].value.toString();
        insert_url = insert_url + "&id=" + id + "&name=" + name + "&address=" + address + "&dean=" + dean + "&campus_id=" + campus_id;
    }
    xmlhttp.open("GET", insert_url, true);
    xmlhttp.send();
}

function deleteRequest(object) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    };
    let delete_url = "/student_management_system/AdminDao?action=delete_" + object;
    if (object === "user") {
        const user = document.getElementsByClassName("user_delete_input");
        const username = user[0].value.toString();
        delete_url = delete_url + "&username=" + username;
    } else if (object === "campus") {
        const campus = document.getElementsByClassName("campus_delete_input");
        const id = campus[0].value.toString();
        const name = campus[1].value.toString();
        delete_url = delete_url + "&id=" + id + "&name=" + name;
    } else if (object === "department") {
        const department = document.getElementsByClassName("department_delete_input");
        const id = department[0].value.toString();
        const name = department[1].value.toString();
        const campus_id = department[2].value.toString();
        delete_url = delete_url + "&id=" + id + "&name=" + name + "&campus_id=" + campus_id;
    }
    xmlhttp.open("GET", delete_url, true);
    xmlhttp.send();
}

function updateRequest(object) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    };
    let update_url = "/student_management_system/AdminDao?action=update_" + object;
    if (object === "user") {
        const user = document.getElementsByClassName("user_update_input");
        const username = user[0].value.toString();
        const password = user[1].value.toString();
        const usertype = user[2].value.toString();
        const foreignid = user[3].value.toString();
        update_url = update_url + "&username=" + username + "&password=" + password + "&usertype=" + usertype + "&foreignid=" + foreignid;
    } else if (object === "campus") {
        const campus = document.getElementsByClassName("campus_update_input");
        const id_old = campus[0].value.toString();
        const name_old = campus[1].value.toString();
        const name_new = campus[2].value.toString();
        const address_new = campus[3].value.toString();
        update_url = update_url + "&id_old=" + id_old + "&name_old" + name_old + "&name_new=" + name_new + "&address_new=" + address_new;
    } else if (object === "department") {
        const department = document.getElementsByClassName("department_update_input");
        const id_old = department[0].value.toString();
        const name_old = department[1].value.toString();
        const name_new = department[2].value.toString();
        const address_new = department[3].value.toString();
        const dean_new = department[4].value.toString();
        const campus_id_new = department[5].value.toString();
        update_url = update_url + "&id_old=" + id_old + "&name_old=" + name_old + "&name_new=" + name_new + "&address_new=" + address_new + "&dean_new=" + dean_new + "&campus_id_new=" + campus_id_new;
    }
    xmlhttp.open("GET", update_url, true);
    xmlhttp.send();
}

// User Info Maintain
function showInsertUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_user'  class='d_form'>"
        + "<h3>请输入新增用户信息</h3>"
        + "<input class='user_insert_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>"
        + "<input class='user_insert_input' id='password' type='password' name='password' value placeholder='密码' required>"
        + "<input class='user_insert_input' id='usertype' type='text' name='usertype' value placeholder='用户类型' required>"
        + "<input class='user_insert_input' id='foreignid' type='text' name='foreignid' value placeholder='工号' required>"
        + "<input id='submit' onclick=insertRequest('user') type='button' name='submit' value='插入'>"
        + "</div>";
}

function showDeleteUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_user'  class='d_form'>"
        + "<h3>请输入待删除用户信息</h3>"
        + "<input class='user_delete_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>"
        + "<input id='submit' onclick=deleteRequest('user') type='button' name='submit' value='删除'>"
        + "</div>";
}

function showUpdateUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_user'  class='d_form'>"
        + "<h3>请输入待修改用户信息</h3>"
        + "<input class='user_update_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>"
        + "<input class='user_update_input' id='password' type='password' name='password' value placeholder='密码' required>"
        + "<input class='user_update_input' id='usertype' type='text' name='usertype' value placeholder='用户类型' required>"
        + "<input class='user_update_input' id='foreignid' type='text' name='foreignid' value placeholder='工号' required>"
        + "<input id='submit' onclick=updateRequest('user') type='button' name='submit' value='修改'>"
        + "</div>";
}

// Campus Info Maintain
function showInsertCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_campus'  class='d_form'>"
        + "<h3>请输入新增校区信息</h3>"
        + "<input class='campus_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='校区编号' required>"
        + "<input class='campus_insert_input' id='name' type='text' name='name' value placeholder='名称' required>"
        + "<input class='campus_insert_input' id='address' type='text' name='address' value placeholder='地址' required>"
        + "<input id='submit' onclick=insertRequest('campus') type='button' name='submit' value='插入'>"
        + "</div>";
}

function showDeleteCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_campus'  class='d_form'>"
        + "<h3>请输入待删除校区信息</h3>"
        + "<input class='campus_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='校区编号' required>"
        + "<input class='campus_delete_input' id='name' type='text' name='name' value placeholder='名称' required>"
        + "<input id='submit' onclick=deleteRequest('campus') type='button' name='submit' value='删除'>"
        + "</div>";
}

function showUpdateCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_campus'  class='d_form'>"
        + "<h3>请输入待修改校区信息</h3>"
        + "<input class='campus_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='原校区编号' required>"
        + "<input class='campus_update_input' id='name_old' type='text' name='name_old' value placeholder='原名称' required>"
        + "<input class='campus_update_input' id='name_new' type='text' name='name_new' value placeholder='新名称' required>"
        + "<input class='campus_update_input' id='address_new' type='text' name='address_new' value placeholder='新地址' required>"
        + "<input id='submit' onclick=updateRequest('campus') type='button' name='submit' value='修改'>"
        + "</div>";
}

// Department Info Maintain
function showInsertDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_department'  class='d_form'>"
        + "<h3>请输入新增专业信息</h3>"
        + "<input class='department_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='专业编号' required>"
        + "<input class='department_insert_input' id='name' type='text' name='name' value placeholder='名称' required>"
        + "<input class='department_insert_input' id='address' type='text' name='address' value placeholder='地址' required>"
        + "<input class='department_insert_input' id='dean' type='text' name='dean' value placeholder='院长' required>"
        + "<input class='department_insert_input' id='campus_id' type='text' name='campus_id' value placeholder='所在校区编号' required>"
        + "<input id='submit' onclick=insertRequest('department') type='button' name='submit' value='插入'>"
        + "</div>";
}

function showDeleteDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_department'  class='d_form'>"
        + "<h3>请输入待删除专业信息</h3>"
        + "<input class='department_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='专业编号' required>"
        + "<input class='department_delete_input' id='name' type='text' name='name' value placeholder='名称' required>"
        + "<input class='department_delete_input' id='campus_id' type='text' name='campus_id' value placeholder='所在校区编号' required>"
        + "<input id='submit' onclick=deleteRequest('department') type='button' name='submit' value='删除'>"
        + "</div>";
}

function showUpdateDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_department'  class='d_form'>"
        + "<h3>请输入待修改专业信息</h3>"
        + "<input class='department_delete_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='原专业编号' required>"
        + "<input class='department_delete_input' id='name_old' type='text' name='name_old' value placeholder='原名称' required>"
        + "<input class='department_insert_input' id='name_new' type='text' name='name_new' value placeholder='名称' required>"
        + "<input class='department_insert_input' id='address_new' type='text' name='address_new' value placeholder='地址' required>"
        + "<input class='department_insert_input' id='dean_new' type='text' name='dean_new' value placeholder='院长' required>"
        + "<input class='department_insert_input' id='campus_id_new' type='text' name='campus_id_new' value placeholder='所在校区编号' required>"
        + "<input id='submit' onclick=updateRequest('department') type='button' name='submit' value='删除'>"
        + "</div>";
}