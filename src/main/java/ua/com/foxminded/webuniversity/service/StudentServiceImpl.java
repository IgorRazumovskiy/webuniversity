package ua.com.foxminded.webuniversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;
import ua.com.foxminded.webuniversity.exception.ExcessNumberStudentsInGroupException;

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
        if (studentRepository.findByGroupId(student.getGroup().getId()).size() < student.getGroup()
                .getMaxNumberOfStudents()) {
            return studentRepository.save(student);
        } else {
            throw new ExcessNumberStudentsInGroupException(
                    "Too much student in group id = " + student.getGroup().getId());
        }
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

    public List<Student> findStudentsByGroup(Integer groupId) {
        return studentRepository.findByGroupId(groupId);
    }

}
