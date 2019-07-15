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

import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.service.GroupService;
import ua.com.foxminded.webuniversity.service.StudentService;

@RestController
@RequestMapping("/groups")
public class GroupRestController {

    private GroupService groupService;
    private StudentService studentService;

    public GroupRestController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("{groupId}")
    public ResponseEntity<Group> findOne(@PathVariable Integer groupId) {
        return ResponseEntity.ok(groupService.findOne(groupId));
    }

    @GetMapping("{groupId}/students")
    public ResponseEntity<List<Student>> findAllByGroupId(@PathVariable Integer groupId) {
        return ResponseEntity.ok(studentService.findStudentsByGroup(groupId));
    }

    @PostMapping()
    public ResponseEntity<Group> create(@RequestBody Group group) {
        return new ResponseEntity<Group>(groupService.create(group), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Group> update(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.update(group));
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<String> delete(@PathVariable Integer groupId) {
        groupService.delete(groupId);
        return ResponseEntity.ok("Delete group with id = " + groupId);
    }

}
