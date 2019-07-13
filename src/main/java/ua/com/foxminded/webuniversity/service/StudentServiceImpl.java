package ua.com.foxminded.webuniversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public Student findOne(Integer id) {
        Optional<Student> result = studentRepository.findById(id);
        Student student = null;
        if (result.isPresent()) {
            student = result.get();
        } else {
            throw new EntityNotFoundException("Cannot find student id = " + id);
        }
        return student;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void delete(Integer id) {
        Optional<Student> result = studentRepository.findById(id);
        if (result.isPresent()) {
            studentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cannot find student id = " + id);
        }
    }

}
