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
    xmlhttp.open("GET","/student_management_system/AdminDao?action=query_all_" + object, true);
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
    if(object === "user") {
        const user = document.getElementsByClassName("user_insert_input");
        const username = user[0].value.toString();
        const password = user[1].value.toString();
        const usertype = user[2].value.toString();
        const foreignid = user[3].value.toString();
        insert_url = insert_url + "&username=" + username + "&password=" + password + "&usertype=" + usertype + "&foreignid=" + foreignid;
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
    }
    xmlhttp.open("GET", update_url, true);
    xmlhttp.send();
}

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
    result.innerHTML = "<div id='show_insert_user'  class='d_form'>"
        + "<h3>请输入待删除用户信息</h3>"
        + "<input class='user_delete_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>"
        + "<input id='submit' onclick=deleteRequest('user') type='button' name='submit' value='删除'>"
        + "</div>";
}

function showUpdateUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_user'  class='d_form'>"
        + "<h3>请输入待修改用户信息</h3>"
        + "<input class='user_update_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>"
        + "<input class='user_update_input' id='password' type='password' name='password' value placeholder='密码' required>"
        + "<input class='user_update_input' id='usertype' type='text' name='usertype' value placeholder='用户类型' required>"
        + "<input class='user_update_input' id='foreignid' type='text' name='foreignid' value placeholder='工号' required>"
        + "<input id='submit' onclick=updateRequest('user') type='button' name='submit' value='修改'>"
        + "</div>";
}
