package ua.com.foxminded.webuniversity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.webuniversity.dao.DAOException;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.service.StudentService;

@RestController
@RequestMapping("/webuniversity")
public class StudentRestController {
    private StudentService studentService; 
    
    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> findAll() {
        return studentService.findAll();
    }
    
    @GetMapping("/students/{studentId}")
    public Student findOne(@PathVariable Integer studentId) {
        return studentService.findOne(studentId);
    }
    
    @PostMapping("/students")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }
    
    @PutMapping("/students")
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }
    
    @DeleteMapping("/students/{studentId}")
    public String delete(@PathVariable Integer studentId) {
        Student student = studentService.findOne(studentId);
        if (student == null) {
            throw new DAOException("Cannot find student id = " + studentId);
        }
        studentService.delete(studentId);
        return "Delete student with id = " + studentId;      
    }
    
}
