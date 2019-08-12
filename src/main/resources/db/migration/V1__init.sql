CREATE TABLE faculty
(
    id serial PRIMARY KEY,
    name character varying(30) NOT NULL
);

CREATE TABLE chair
(
    id serial PRIMARY KEY,
    name character varying(30) NOT NULL,
    faculty_id int,
    CONSTRAINT chair_faculty_fk FOREIGN KEY (faculty_id) 
        REFERENCES faculty (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE classroom
(
    id serial PRIMARY KEY,
    number character varying(30) NOT NULL,
    capacity int NOT NULL,
    faculty_id int,
    CONSTRAINT classroom_faculty_fk FOREIGN KEY (faculty_id)
        REFERENCES faculty (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE groups
(
    id serial PRIMARY KEY,
    name character varying(30) NOT NULL,
    faculty_id int,
    max_number_of_students int NOT NULL,
    CONSTRAINT group_faculty_fk FOREIGN KEY (faculty_id)
        REFERENCES faculty (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE student
(
    id serial PRIMARY KEY,
    name character varying(50) NOT NULL,
    group_id int,
    CONSTRAINT student_group_fk FOREIGN KEY (group_id)
        REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE course
(
    id serial PRIMARY KEY,
    name character varying(30) NOT NULL,
    chair_id int,
    CONSTRAINT course_chair_fk FOREIGN KEY (chair_id)
        REFERENCES chair (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE teacher
(
    id serial PRIMARY KEY,
    name character varying(50) NOT NULL,
    chair_id int,
    CONSTRAINT teacher_chair_fk FOREIGN KEY (chair_id)
        REFERENCES chair (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE course_teacher
(
    course_id int NOT NULL,
    teacher_id int NOT NULL,
    CONSTRAINT course_teacher_fk0 FOREIGN KEY (course_id)
        REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT course_teacher_fk1 FOREIGN KEY (teacher_id)
        REFERENCES teacher (id) ON UPDATE CASCADE ON DELETE CASCADE    
);

CREATE TABLE lesson
(
    id serial PRIMARY KEY,
    teacher_id int NOT NULL,
    group_id int NOT NULL,
    classroom_id int NOT NULL,
    course_id int NOT NULL,
    dateTime timestamp,
    CONSTRAINT lesson_teacher_fk FOREIGN KEY (teacher_id)
        REFERENCES teacher (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT lesson_group_fk FOREIGN KEY (group_id)
        REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,    
    CONSTRAINT lesson_classroom_fk FOREIGN KEY (classroom_id)
        REFERENCES classroom (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT lesson_course_fk FOREIGN KEY (course_id)
        REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE
);
