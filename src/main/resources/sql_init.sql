create schema if not exists mydb collate utf8_general_ci;

create table if not exists Campus
(
    Campus_ID varchar(50) not null,
    Campus_Name varchar(50) not null,
    Campus_Address varchar(200) null,
    constraint Campus_ID_UNIQUE
        unique (Campus_ID)
);

alter table Campus
    add primary key (Campus_ID);

create table if not exists Department
(
    Department_ID varchar(50) not null,
    Department_Name varchar(50) null,
    Department_Address varchar(200) null,
    Department_Dean varchar(50) null,
    Department_Campus_ID varchar(50) not null,
    constraint Department_ID_UNIQUE
        unique (Department_ID),
    constraint fk_Department_Campus
        foreign key (Department_Campus_ID) references Campus (Campus_ID)
);

create index fk_Department_Campus_idx
    on Department (Department_Campus_ID);

alter table Department
    add primary key (Department_ID);

create table if not exists Person
(
    ID_Card_Number varchar(50) not null,
    ID_Card_Type tinyint default 0 not null,
    Name varchar(45) charset utf16 not null,
    Gender tinyint not null,
    Birth date not null,
    Nationality varchar(45) charset utf16 not null,
    Address varchar(45) null,
    Address_ID varchar(45) null,
    Address_Phone_Number varchar(45) null,
    Address_Postal_Code varchar(45) null,
    constraint ID_Card_Number_UNIQUE
        unique (ID_Card_Number)
);

alter table Person
    add primary key (ID_Card_Number);

create table if not exists Teacher
(
    Teacher_ID varchar(50) not null,
    Teacher_Enroll_Date date not null,
    Teacher_Department_ID varchar(50) null,
    Teacher_Title varchar(45) null,
    Person_ID_Card_Number varchar(50) not null,
    constraint Teacher_ID_UNIQUE
        unique (Teacher_ID),
    constraint fk_Teacher_Department1
        foreign key (Teacher_Department_ID) references Department (Department_ID),
    constraint fk_Teacher_Person1
        foreign key (Person_ID_Card_Number) references Person (ID_Card_Number)
);

create index fk_Teacher_Department1_idx
    on Teacher (Teacher_Department_ID);

create index fk_Teacher_Person1_idx
    on Teacher (Person_ID_Card_Number);

alter table Teacher
    add primary key (Teacher_ID);

create table if not exists Class
(
    Class_ID varchar(50) not null,
    Class_Name varchar(45) not null,
    Class_Date date not null,
    Class_Grade varchar(45) not null,
    Class_Department_ID varchar(50) not null,
    Class_Head_Teacher_ID varchar(50) not null,
    constraint Class_ID_UNIQUE
        unique (Class_ID),
    constraint fk_Class_Department1
        foreign key (Class_Department_ID) references Department (Department_ID),
    constraint fk_Class_Teacher1
        foreign key (Class_Head_Teacher_ID) references Teacher (Teacher_ID)
)
    comment '																						';

create index fk_Class_Department1_idx
    on Class (Class_Department_ID);

create index fk_Class_Teacher1_idx
    on Class (Class_Head_Teacher_ID);

alter table Class
    add primary key (Class_ID);

create table if not exists Course
(
    Course_ID varchar(50) not null,
    Course_Name varchar(50) not null,
    Course_Department_ID varchar(50) not null,
    Course_Teacher_ID varchar(50) not null,
    Course_Exam_Type varchar(45) null,
    Course_Semester varchar(45) null,
    Course_Year varchar(45) null,
    Course_Time varchar(45) null,
    constraint Course_ID_UNIQUE
        unique (Course_ID),
    constraint fk_Course_Department1
        foreign key (Course_Department_ID) references Department (Department_ID),
    constraint fk_Course_Teacher1
        foreign key (Course_Teacher_ID) references Teacher (Teacher_ID)
);

create index fk_Course_Department1_idx
    on Course (Course_Department_ID);

create index fk_Course_Teacher1_idx
    on Course (Course_Teacher_ID);

alter table Course
    add primary key (Course_ID);

create table if not exists Transaction
(
    Transaction_ID varchar(50) not null
        primary key,
    Transaction_Type tinyint not null,
    Transaction_Date date not null,
    Transaction_Origin_Class_ID varchar(50) not null,
    Transaction_Current_Class_ID varchar(50) not null,
    Transaction_League_Member varchar(45) not null,
    Transaction_Reason varchar(50) not null,
    constraint fk_Transaction_Class1
        foreign key (Transaction_Origin_Class_ID) references Class (Class_ID),
    constraint fk_Transaction_Class2
        foreign key (Transaction_Current_Class_ID) references Class (Class_ID)
);

create table if not exists Student
(
    Student_ID varchar(50) not null,
    Student_Enroll_Date date not null,
    Student_Email varchar(45) not null,
    Student_Class_ID varchar(50) not null,
    Student_Transaction_ID varchar(50) not null,
    Person_ID_Card_Number varchar(50) not null,
    constraint Student_ID_UNIQUE
        unique (Student_ID),
    constraint fk_Student_Class1
        foreign key (Student_Class_ID) references Class (Class_ID),
    constraint fk_Student_Person1
        foreign key (Person_ID_Card_Number) references Person (ID_Card_Number),
    constraint fk_Student_Transaction1
        foreign key (Student_Transaction_ID) references Transaction (Transaction_ID)
);

create index fk_Student_Class1_idx
    on Student (Student_Class_ID);

create index fk_Student_Person1_idx
    on Student (Person_ID_Card_Number);

create index fk_Student_Transaction1_idx
    on Student (Student_Transaction_ID);

alter table Student
    add primary key (Student_ID);

create table if not exists CourseSelection
(
    CourseSelection_ID int auto_increment
        primary key,
    CourseSelection_Course_ID varchar(50) not null,
    CourseSelection_Student_ID varchar(50) not null,
    CourseSelection_Date varchar(45) null,
    CourseSelection_Semester varchar(45) null,
    Score int null,
    constraint fk_CourseSelection_Course1
        foreign key (CourseSelection_Course_ID) references Course (Course_ID),
    constraint fk_CourseSelection_Student1
        foreign key (CourseSelection_Student_ID) references Student (Student_ID)
);

create index fk_CourseSelection_Course1_idx
    on CourseSelection (CourseSelection_Course_ID);

create index fk_CourseSelection_Student1_idx
    on CourseSelection (CourseSelection_Student_ID);

create definer = admin@localhost trigger CourseSelection_BEFORE_INSERT
    before insert
    on CourseSelection
    for each row
BEGIN
    SET NEW.CourseSelection_Semester =
            (SELECT Course_Semester FROM Course WHERE Course.Course_ID = NEW.CourseSelection_ID);
END;

create definer = admin@localhost trigger Student_BEFORE_UPDATE
    before update
    on Student
    for each row
BEGIN
    IF NOT OLD.Student_Transaction_ID = NULL THEN
        SIGNAL SQLSTATE '01000'
            SET MESSAGE_TEXT = 'This Student Has Transaction Before', MYSQL_ERRNO = 1000;
    END IF;
END;

create index fk_Transaction_Class1_idx
    on Transaction (Transaction_Origin_Class_ID);

create index fk_Transaction_Class2_idx
    on Transaction (Transaction_Current_Class_ID);

create table if not exists User
(
    id int auto_increment,
    username varchar(45) not null,
    password varchar(45) not null,
    usertype int not null,
    foreign_id varchar(45) null,
    constraint id_UNIQUE
        unique (id),
    constraint username_UNIQUE
        unique (username)
);

alter table User
    add primary key (id);

