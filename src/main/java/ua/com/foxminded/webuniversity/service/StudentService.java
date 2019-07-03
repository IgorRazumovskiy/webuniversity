package ua.com.foxminded.webuniversity.service;

import java.util.List;

import ua.com.foxminded.webuniversity.entity.Student;

public interface StudentService {

    public Student create(Student student);

    public Student update(Student student);

    public Student findOne(Integer id);

    public List<Student> findAll();

    public void delete(Integer id);

}
