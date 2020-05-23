//折叠菜单效果
$(function () {
    const Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        const links = this.el.find('.link');

        links.on('click', {
            el: this.el,
            multiple: this.multiple
        }, this.dropdown)
    };

    Accordion.prototype.dropdown = function (e) {
        const $el = e.data.el;

        let $this = $(this),
            $next = $this.next();

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
        insert_url = insert_url +
            "&username=" + username +
            "&password=" + password +
            "&usertype=" + usertype +
            "&foreignid=" + foreignid;
    } else if (object === "campus") {
        // Insert Campus
        const campus = document.getElementsByClassName("campus_insert_input");
        const id = campus[0].value.toString();
        const name = campus[1].value.toString();
        const address = campus[2].value.toString();
        insert_url = insert_url +
            "&id=" + id +
            "&name=" + name +
            "&address=" + address;
    } else if (object === "department") {
        // Insert Department
        const department = document.getElementsByClassName("department_insert_input");
        const id = department[0].value.toString();
        const name = department[1].value.toString();
        const address = department[2].value.toString();
        const dean = department[3].value.toString();
        const campus_id = department[4].value.toString();
        insert_url = insert_url +
            "&id=" + id +
            "&name=" + name +
            "&address=" + address +
            "&dean=" + dean +
            "&campus_id=" + campus_id;
    } else if (object === "class") {
        // Insert Class
        const class_ = document.getElementsByClassName("class_insert_input");
        const id = class_[0].value.toString();
        const name = class_[1].value.toString();
        const date = class_[2].value.toString();
        const grade = class_[3].value.toString();
        const department_id = class_[4].value.toString();
        const head_teacher_id = class_[5].value.toString();
        insert_url = insert_url +
            "&id=" + id +
            "&name=" + name +
            "&date=" + date +
            "&grade=" + grade +
            "&department_id=" + department_id +
            "&head_teacher_id=" + head_teacher_id;
    } else if (object === "student") {
        // Insert student
        const student = document.getElementsByClassName("student_insert_input");
        const id = student[0].value.toString();
        const enrollment_date = student[1].value.toString();
        const email = student[2].value.toString();
        const class_id = student[3].value.toString();
        const id_card_number = student[4].value.toString();
        const card_type = student[5].value.toString();
        const name = student[6].value.toString();
        const gender = student[7].value.toString();
        const birthdate = student[8].value.toString();
        const nationality = student[9].value.toString();
        const address = student[10].value.toString();
        const address_postal_code = student[11].value.toString();
        const address_phone_number = student[12].value.toString();

        insert_url = insert_url +
            "&id=" + id +
            "&enrollment_date=" + enrollment_date +
            "&email=" + email +
            "&class_id=" + class_id;
        insert_url = insert_url +
            "&id_card_number=" + id_card_number +
            "&card_type=" + card_type +
            "&name=" + name +
            "&gender=" + gender +
            "&birthdate=" + birthdate +
            "&nationality=" + nationality +
            "&address=" + address +
            "&address_postal_code=" + address_postal_code +
            "&address_phone_number=" + address_phone_number;
    } else if (object === "teacher") {
        // Insert teacher
        const teacher = document.getElementsByClassName("teacher_insert_input");
        const id = teacher[0].value.toString();
        const enrollment_date = teacher[1].value.toString();
        const department_id = teacher[2].value.toString();
        const teacher_title = teacher[3].value.toString();
        const id_card_number = teacher[4].value.toString();
        const card_type = teacher[5].value.toString();
        const name = teacher[6].value.toString();
        const gender = teacher[7].value.toString();
        const birthdate = teacher[8].value.toString();
        const nationality = teacher[9].value.toString();
        const address = teacher[10].value.toString();
        const address_postal_code = teacher[11].value.toString();
        const address_phone_number = teacher[12].value.toString();

        insert_url = insert_url +
            "&id=" + id +
            "&enrollment_date=" + enrollment_date +
            "&department_id=" + department_id +
            "&teacher_title=" + teacher_title;
        insert_url = insert_url +
            "&id_card_number=" + id_card_number +
            "&card_type=" + card_type +
            "&name=" + name +
            "&gender=" + gender +
            "&birthdate=" + birthdate +
            "&nationality=" + nationality +
            "&address=" + address +
            "&address_postal_code=" + address_postal_code +
            "&address_phone_number=" + address_phone_number;
    } else if (object === "course") {
        const course = document.getElementsByClassName("course_insert_input");
        const id = course[0].value.toString();
        const name = course[1].value.toString();
        const department_id = course[2].value.toString();
        const exam_type = course[3].value.toString();

        insert_url = insert_url +
            "&id=" + id +
            "&name=" + name +
            "&department_id=" + department_id +
            "&exam_type=" + exam_type;
    } else if (object === "selection") {
        const today = new Date();
        const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
        const selection = document.getElementsByClassName("selection_insert_input");
        const student_id = selection[0].value.toString();
        const course_id = selection[1].value.toString();

        insert_url = insert_url +
            "&student_id=" + student_id +
            "&course_id=" + course_id +
            "&date=" + date;
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
        delete_url = delete_url +
            "&username=" + username;
    } else if (object === "campus") {
        const campus = document.getElementsByClassName("campus_delete_input");
        const id = campus[0].value.toString();
        const name = campus[1].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&name=" + name;
    } else if (object === "department") {
        const department = document.getElementsByClassName("department_delete_input");
        const id = department[0].value.toString();
        const name = department[1].value.toString();
        const campus_id = department[2].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&name=" + name +
            "&campus_id=" + campus_id;
    } else if (object == "class") {
        const class_ = document.getElementsByClassName("class_delete_input");
        const id = class_[0].value.toString();
        const name = class_[1].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&name=" + name;
    } else if (object === "student") {
        const student = document.getElementsByClassName("student_delete_input");
        const id = student[0].value.toString();
        const id_card_number = student[1].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&id_card_number=" + id_card_number;
    } else if (object === "teacher") {
        const teacher = document.getElementsByClassName("teacher_delete_input");
        const id = teacher[0].value.toString();
        const id_card_number = teacher[1].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&id_card_number=" + id_card_number;
    } else if (object === "course") {
        const course = document.getElementsByClassName("course_delete_input");
        const id = course[0].value.toString();
        const name = course[1].value.toString();
        const department_id = course[2].value.toString();
        delete_url = delete_url +
            "&id=" + id +
            "&name=" + name +
            "&department_id=" + department_id;
    } else if (object === "selection") {
        const selection = document.getElementsByClassName("selection_delete_input");
        const student_id = selection[0].value.toString();
        const course_id = selection[1].value.toString();
        delete_url = delete_url +
            "&student_id=" + student_id +
            "&course_id=" + course_id;
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
        update_url = update_url +
            "&username=" + username +
            "&password=" + password +
            "&usertype=" + usertype +
            "&foreignid=" + foreignid;
    } else if (object === "campus") {
        const campus = document.getElementsByClassName("campus_update_input");
        const id_old = campus[0].value.toString();
        const name_old = campus[1].value.toString();
        const name_new = campus[2].value.toString();
        const address_new = campus[3].value.toString();
        update_url = update_url +
            "&id_old=" + id_old +
            "&name_old" + name_old +
            "&name_new=" + name_new +
            "&address_new=" + address_new;
    } else if (object === "department") {
        const department = document.getElementsByClassName("department_update_input");
        const id_old = department[0].value.toString();
        const name_old = department[1].value.toString();
        const name_new = department[2].value.toString();
        const address_new = department[3].value.toString();
        const dean_new = department[4].value.toString();
        const campus_id_new = department[5].value.toString();
        update_url = update_url +
            "&id_old=" + id_old +
            "&name_old=" + name_old +
            "&name_new=" + name_new +
            "&address_new=" + address_new +
            "&dean_new=" + dean_new +
            "&campus_id_new=" + campus_id_new;
    } else if (object === "class") {
        const class_ = document.getElementsByClassName("class_update_input");
        const id_old = class_[0].value.toString();
        const name_old = class_[1].value.toString();
        const name_new = class_[2].value.toString();
        const date_new = class_[3].value.toString();
        const department_id_new = class_[4].value.toString();
        const head_teacher_id_new = class_[5].value.toString();
        update_url = update_url +
            "&id_old=" + id_old +
            "&name_old=" + name_old +
            "&name_new=" + name_new +
            "&date_new=" + date_new +
            "&department_id_new" + department_id_new +
            "&head_teacher_id_new" + head_teacher_id_new;
    } else if (object === "student") {
        const student = document.getElementsByClassName("student_update_input");
        const id_old = student[0].value.toString();
        const strinfo1 = student[1].value.toString();
        const strinfo2 = student[2].value.toString();
        const strinfo3 = student[3].value.toString();
        update_url = update_url +
            "&id_old=" + id_old +
            "&strinfo1=" + strinfo1 +
            "&strinfo2=" + strinfo2 +
            "&strinfo3=" + strinfo3;
    } else if (object === "teacher") {
        const student = document.getElementsByClassName("student_update_input");
        const id_old = student[0].value.toString();
        const strinfo1 = student[1].value.toString();
        const strinfo2 = student[2].value.toString();
        const strinfo3 = student[3].value.toString();
        update_url = update_url +
            "&id_old=" + id_old +
            "&strinfo1=" + strinfo1 +
            "&strinfo2=" + strinfo2 +
            "&strinfo3=" + strinfo3;
    } else if (object === "course") {
        const course = document.getElementsByClassName("course_update_input");
        const id = course[0].value.toString();
        const teacher_id = course[1].value.toString();
        const semester = course[2].value.toString();
        const year = course[3].value.toString();
        const time = course[4].value.toString();
        update_url = update_url +
            "&id=" + id +
            "&teacher_id=" + teacher_id +
            "&semester=" + semester +
            "&year=" + year +
            "&time=" + time;
    }
    xmlhttp.open("GET", update_url, true);
    xmlhttp.send();
}

function infoqueryRequest(object) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    };
    let infoquery_url = "/student_management_system/AdminDao?action=infoquery_" + object;

    if (object === "student") {
    } else if (object === "teacher") {
    }
}

// User Info Maintain
function showInsertUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_user'  class='d_form'>" +
        "<h3>请输入新增用户信息</h3>" +
        "<input class='user_insert_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>" +
        "<input class='user_insert_input' id='password' type='password' name='password' value placeholder='密码' required>" +
        "<input class='user_insert_input' id='usertype' type='text' name='usertype' value placeholder='用户类型' required>" +
        "<input class='user_insert_input' id='foreignid' type='text' name='foreignid' value placeholder='工号'>" +
        "<input id='submit' onclick=insertRequest('user') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_user'  class='d_form'>" +
        "<h3>请输入待删除用户信息</h3>" +
        "<input class='user_delete_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>" +
        "<input id='submit' onclick=deleteRequest('user') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateUser() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_user'  class='d_form'>" +
        "<h3>请输入待修改用户信息</h3>" +
        "<input class='user_update_input' id='username' type='text' autofocus='autofocus' name='username' value placeholder='用户名' required>" +
        "<input class='user_update_input' id='password' type='password' name='password' value placeholder='密码'>" +
        "<input class='user_update_input' id='usertype' type='text' name='usertype' value placeholder='用户类型'>" +
        "<input class='user_update_input' id='foreignid' type='text' name='foreignid' value placeholder='工号'>" +
        "<input id='submit' onclick=updateRequest('user') type='button' name='submit' value='修改'>" +
        "</div>";
}

// Campus Info Maintain
function showInsertCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_campus'  class='d_form'>" +
        "<h3>请输入新增校区信息</h3>" +
        "<input class='campus_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='校区编号' required>" +
        "<input class='campus_insert_input' id='name' type='text' name='name' value placeholder='名称' required>" +
        "<input class='campus_insert_input' id='address' type='text' name='address' value placeholder='地址'>" +
        "<input id='submit' onclick=insertRequest('campus') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_campus'  class='d_form'>" +
        "<h3>请输入待删除校区信息</h3>" +
        "<input class='campus_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='校区编号'>" +
        "<input class='campus_delete_input' id='name' type='text' name='name' value placeholder='名称'>" +
        "<input id='submit' onclick=deleteRequest('campus') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateCampus() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_campus'  class='d_form'>" +
        "<h3>请输入待修改校区信息</h3>" +
        "<input class='campus_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='原校区编号'>" +
        "<input class='campus_update_input' id='name_old' type='text' name='name_old' value placeholder='原名称'>" +
        "<input class='campus_update_input' id='name_new' type='text' name='name_new' value placeholder='新名称'>" +
        "<input class='campus_update_input' id='address_new' type='text' name='address_new' value placeholder='新地址'>" +
        "<input id='submit' onclick=updateRequest('campus') type='button' name='submit' value='修改'>" +
        "</div>";
}

// Department Info Maintain
function showInsertDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_department'  class='d_form'>" +
        "<h3>请输入新增专业信息</h3>" +
        "<input class='department_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='专业编号' required>" +
        "<input class='department_insert_input' id='name' type='text' name='name' value placeholder='名称' required>" +
        "<input class='department_insert_input' id='address' type='text' name='address' value placeholder='地址'>" +
        "<input class='department_insert_input' id='dean' type='text' name='dean' value placeholder='院长'>" +
        "<input class='department_insert_input' id='campus_id' type='text' name='campus_id' value placeholder='所在校区编号'>" +
        "<input id='submit' onclick=insertRequest('department') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_department'  class='d_form'>" +
        "<h3>请输入待删除专业信息</h3>" +
        "<input class='department_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='专业编号'>" +
        "<input class='department_delete_input' id='name' type='text' name='name' value placeholder='名称'>" +
        "<input class='department_delete_input' id='campus_id' type='text' name='campus_id' value placeholder='所在校区编号'>" +
        "<input id='submit' onclick=deleteRequest('department') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateDepartment() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_department'  class='d_form'>" +
        "<h3>请输入待修改专业信息</h3>" +
        "<input class='department_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='原专业编号'>" +
        "<input class='department_update_input' id='name_old' type='text' name='name_old' value placeholder='原名称'>" +
        "<input class='department_update_input' id='name_new' type='text' name='name_new' value placeholder='名称'>" +
        "<input class='department_update_input' id='address_new' type='text' name='address_new' value placeholder='地址'>" +
        "<input class='department_update_input' id='dean_new' type='text' name='dean_new' value placeholder='院长'>" +
        "<input class='department_update_input' id='campus_id_new' type='text' name='campus_id_new' value placeholder='所在校区编号'>" +
        "<input id='submit' onclick=updateRequest('department') type='button' name='submit' value='修改'>" +
        "</div>";
}

// Class Info Maintain
function showInsertClass() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_class'  class='d_form'>" +
        "<h3>请输入新增班级信息</h3>" +
        "<input class='class_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='班级编号' required>" +
        "<input class='class_insert_input' id='name' type='text' name='name' value placeholder='名称' required>" +
        "<input class='class_insert_input' id='date' type='text' name='date' value placeholder='建立日期'>" +
        "<input class='class_insert_input' id='grade' type='text' name='grade' value placeholder='年级'>" +
        "<input class='class_insert_input' id='department_id' type='text' name='department_id' value placeholder='所在专业编号'>" +
        "<input class='class_insert_input' id='head_teacher_id' type='text' name='head_teacher_id' value placeholder='班主任工号'>" +
        "<input id='submit' onclick=insertRequest('class') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteClass() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_class'  class='d_form'>" +
        "<h3>请输入待删除班级信息</h3>" +
        "<input class='class_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='班级编号'>" +
        "<input class='class_delete_input' id='name' type='text' name='name' value placeholder='名称'>" +
        "<input id='submit' onclick=deleteRequest('class') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateClass() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_class'  class='d_form'>" +
        "<h3>请输入待修改班级信息</h3>" +
        "<input class='class_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='原专业编号'>" +
        "<input class='class_update_input' id='name_old' type='text' name='name_old' value placeholder='原名称'>" +
        "<input class='class_update_input' id='name_new' type='text' name='name_new' value placeholder='名称'>" +
        "<input class='class_update_input' id='date_new' type='text' name='date_new' value placeholder='地址'>" +
        "<input class='class_update_input' id='grade_new' type='text' name='grade_new' value placeholder='院长'>" +
        "<input class='class_update_input' id='department_id_new' type='text' name='department_id_new' value placeholder='所在校区编号'>" +
        "<input class='class_update_input' id='head_teacher_id_new' type='text' name='head_teacher_id_new' value placeholder='所在校区编号'>" +
        "<input id='submit' onclick=updateRequest('class') type='button' name='submit' value='修改'>" +
        "</div>";
}

// Student & Teacher Info Maintain
function showInsertStudent() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_student'  class='d_form'>" +
        "<h3>请输入新增学生信息</h3>" +
        "<input class='student_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='学号' required>" +
        "<input class='student_insert_input' id='enrollment_date' type='text' name='enrollment_date' value placeholder='注册日期'>" +
        "<input class='student_insert_input' id='email' type='text' name='email' value placeholder='Email'>" +
        "<input class='student_insert_input' id='class_id' type='text' name='class_id' value placeholder='所在班级号'>" +
        "<input class='student_insert_input' id='id_card_number' type='text' name='id_card_number' value placeholder='证件号' required>" +
        "<input class='student_insert_input' id='card_type' type='text' name='card_type' value placeholder='证件类型（0：身份证，1：护照）' required>" +
        "<input class='student_insert_input' id='name' type='text' name='name' value placeholder='姓名' required>" +
        "<input class='student_insert_input' id='gender' type='text' name='gender' value placeholder='性别（0：男，1：女）' required>" +
        "<input class='student_insert_input' id='birthdate' type='text' name='birthdate' value placeholder='出生日期'>" +
        "<input class='student_insert_input' id='nationality' type='text' name='nationality' value placeholder='国籍'>" +
        "<input class='student_insert_input' id='address' type='text' name='address' value placeholder='住址'>" +
        "<input class='student_insert_input' id='address_postal_code' type='text' name='address_postal_code' value placeholder='地址邮编'>" +
        "<input class='student_insert_input' id='address_phone_number' type='text' name='address_phone_number' value placeholder='地址电话'>" +
        "<input id='submit' onclick=insertRequest('student') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteStudent() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_student'  class='d_form'>" +
        "<h3>请输入待删除学生信息</h3>" +
        "<input class='student_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='学工号'>" +
        "<input class='student_delete_input' id='id_card_number' type='text' name='id_card_number' value placeholder='证件号'>" +
        "<input id='submit' onclick=deleteRequest('student') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateStudent() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_student'  class='d_form'>" +
        "<h3>请输入待修改学生信息</h3>" +
        "<input class='student_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='学号'>" +
        "<input class='student_update_input' id='enrollment_date_new' type='text' name='enrollment_date_new' value placeholder='新注册日期'>" +
        "<input class='student_update_input' id='class_id_new' type='text' name='class_id_new' value placeholder='新班级号'>" +
        "<input class='student_update_input' id='email_new' type='text' name='email_new' value placeholder='电子邮箱'>" +
        "<input id='submit' onclick=updateRequest('student') type='button' name='submit' value='修改'>" +
        "</div>";
}

function showInsertTeacher() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_teacher'  class='d_form'>" +
        "<h3>请输入新增教师信息</h3>" +
        "<input class='teacher_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='工号' required>" +
        "<input class='teacher_insert_input' id='enrollment_date' type='text' name='enrollment_date' value placeholder='注册日期'>" +
        "<input class='teacher_insert_input' id='department_id' type='text' name='department_id' value placeholder='所在专业号'>" +
        "<input class='teacher_insert_input' id='teacher_title' type='text' name='teacher_title' value placeholder='教职职称'>" +
        "<input class='teacher_insert_input' id='id_card_number' type='text' name='id_card_number' value placeholder='证件号' required>" +
        "<input class='teacher_insert_input' id='card_type' type='text' name='card_type' value placeholder='证件类型（0：身份证，1：护照）' required>" +
        "<input class='teacher_insert_input' id='name' type='text' name='name' value placeholder='姓名' required>" +
        "<input class='teacher_insert_input' id='gender' type='text' name='gender' value placeholder='性别（0：男，1：女）' required>" +
        "<input class='teacher_insert_input' id='birthdate' type='text' name='birthdate' value placeholder='出生日期'>" +
        "<input class='teacher_insert_input' id='nationality' type='text' name='nationality' value placeholder='国籍'>" +
        "<input class='teacher_insert_input' id='address' type='text' name='address' value placeholder='住址' required>" +
        "<input class='teacher_insert_input' id='address_postal_code' type='text' name='address_postal_code' value placeholder='地址邮编'>" +
        "<input class='teacher_insert_input' id='address_phone_number' type='text' name='address_phone_number' value placeholder='地址电话'>" +
        "<input id='submit' onclick=insertRequest('teacher') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteTeacher() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_teacher'  class='d_form'>" +
        "<h3>请输入待删除教师信息</h3>" +
        "<input class='teacher_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='学工号'>" +
        "<input class='teacher_delete_input' id='id_card_number' type='text' name='id_card_number' value placeholder='证件号'>" +
        "<input id='submit' onclick=deleteRequest('teacher') type='button' name='submit' value='删除'>" +
        "</div>";

}

function showUpdateTeacher() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_teacher'  class='d_form'>" +
        "<h3>请输入待修改教师信息</h3>" +
        "<input class='teacher_update_input' id='id_old' type='text' autofocus='autofocus' name='id_old' value placeholder='学号'>" +
        "<input class='teacher_update_input' id='enrollment_date_new' type='text' name='enrollment_date_new' value placeholder='新注册日期'>" +
        "<input class='teacher_update_input' id='department_id_new' type='text' name='department_id_new' value placeholder='新班级号'>" +
        "<input class='teacher_update_input' id='title_new' type='text' name='title_new' value placeholder='电子邮箱'>" +
        "<input id='submit' onclick=updateRequest('teacher') type='button' name='submit' value='修改'>" +
        "</div>";
}

// Course Info Maintain
function showInsertCourse() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_teacher'  class='d_form'>" +
        "<h3>请输入待插入课程信息</h3>" +
        "<input class='course_insert_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='课程编号' required>" +
        "<input class='course_insert_input' id='name' type='text' name='name' value placeholder='名称' required>" +
        "<input class='course_insert_input' id='department_id' type='text' name='department_id' value placeholder='开课院系ID'>" +
        "<input class='course_insert_input' id='exam_type' type='text' name='exam_type' value placeholder='考试类型'>" +
        "<input id='submit' onclick=insertRequest('course') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteCourse() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_course'  class='d_form'>" +
        "<h3>请输入待删除课程信息</h3>" +
        "<input class='course_delete_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='学号'>" +
        "<input class='course_delete_input' id='name' type='text' name='name' value placeholder='名称'>" +
        "<input class='course_delete_input' id='department_id' type='text' name='department_id' value placeholder='所在院系编号'>" +
        "<input id='submit' onclick=deleteRequest('course') type='button' name='submit' value='删除'>" +
        "</div>";
}

function showUpdateCourse() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_update_course'  class='d_form'>" +
        "<h3>请输入待开课课程信息</h3>" +
        "<input class='course_update_input' id='id' type='text' autofocus='autofocus' name='id' value placeholder='课程编号'>" +
        "<input class='course_update_input' id='teacher_id' type='text' name='teacher_id' value placeholder='授课教师ID'>" +
        "<input class='course_update_input' id='semester' type='text' name='semester' value placeholder='开课学期'>" +
        "<input class='course_update_input' id='year' type='text' name='year' value placeholder='开课年份'>" +
        "<input class='course_update_input' id='time' type='text' name='time' value placeholder='开课时间'>" +
        "<input id='submit' onclick=updateRequest('course') type='button' name='submit' value='插入'>"
    "</div>";
}

function showInsertSelection() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_insert_selection'  class='d_form'>" +
        "<h3>请输入选课信息</h3>" +
        "<input class='selection_insert_input' id='student_id' type='text' autofocus='autofocus' name='student_id' value placeholder='学生编号' required>" +
        "<input class='selection_insert_input' id='course_id' type='text' name='course_id' value placeholder='课程编号' required>" +
        "<input id='submit' onclick=insertRequest('selection') type='button' name='submit' value='插入'>" +
        "</div>";
}

function showDeleteSelection() {
    const result = document.getElementById("result");
    result.innerHTML = "<div id='show_delete_selection'  class='d_form'>" +
        "<h3>请输入选课信息</h3>" +
        "<input class='selection_delete_input' id='student_id' type='text' autofocus='autofocus' name='student_id' value placeholder='学生编号' required>" +
        "<input class='selection_delete_input' id='course_id' type='text' name='course_id' value placeholder='课程编号' required>" +
        "<input id='submit' onclick=deleteRequest('selection') type='button' name='submit' value='删除'>" +
        "</div>";
}

// Special Query I
function infoQureyStudent() {
    const result = document.getElementById("result");
    result.innerHTML = "<div class='show_queryinfo_student'  class='d_form'>" +
        "<h3>请输入学号</h3>" +
        "<input class='infoquery_student_input' id='student_id' type='text' autofocus='autofocus' name='student_id' value placeholder='学号' required>" +
        "<input id='submit' onclick=infoqueryRequest('student') type='button' name='submit' value='插入'>" +
        "</div>";
}

function infoQureyTeacher() {
    const result = document.getElementById("result");
    result.innerHTML = "<div class='show_queryinfo_teacher'  class='d_form'>" +
        "<h3>请输入工号</h3>" +
        "<input class='infoquery_teacher_input' id='teacher_id' type='text' autofocus='autofocus' name='teacher_id' value placeholder='工号' required>" +
        "<input id='submit' onclick=infoqueryRequest('teacher') type='button' name='submit' value='插入'>" +
        "</div>";
}

function infoQueryCourse() {

}