package ua.com.foxminded.webuniversity.service;

import java.util.List;

import ua.com.foxminded.webuniversity.entity.Student;

public interface StudentService {

    Student create(Student student);

    Student update(Student student);

    Student findOne(Integer id);

    List<Student> findAll();

    void delete(Integer id);

    List<Student> findStudentsByGroup(Integer groupId);

}
