package ua.com.foxminded.webuniversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;
import ua.com.foxminded.webuniversity.exception.StudentsLimitExceededException;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private GroupService groupService;

    public StudentServiceImpl(StudentRepository studentRepository, GroupService groupService) {
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        Group group = groupService.findOne(student.getGroup().getId());
        if (group.getStudents().size() >= group.getMaxNumberOfStudents()) {
            throw new StudentsLimitExceededException("Too much students in " + group);
        }
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

    public List<Student> findStudentsByGroup(Integer groupId) {
        return studentRepository.findByGroupId(groupId);
    }

}
