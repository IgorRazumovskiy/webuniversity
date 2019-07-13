package ua.com.foxminded.webuniversity.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> findOne(@PathVariable Integer studentId) {
        return ResponseEntity.ok(studentService.findOne(studentId));
    }

    @PostMapping()
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return new ResponseEntity<Student>(studentService.create(student), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(student));
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<String> delete(@PathVariable Integer studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok("Delete student with id = " + studentId);
    }

}
