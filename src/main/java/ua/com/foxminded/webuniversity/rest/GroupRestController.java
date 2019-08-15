package ua.com.foxminded.webuniversity.rest;

import java.util.List;

import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.service.GroupService;
import ua.com.foxminded.webuniversity.service.StudentService;

@Api(value = "CRUD group", description = "group endpoints")
@RestController
@RequestMapping("/groups")
public class GroupRestController {

    private GroupService groupService;
    private StudentService studentService;

    public GroupRestController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @ApiOperation(value = "get all groups")
    @GetMapping()
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @ApiOperation(value = "find group by id")
    @GetMapping("{groupId}")
    public ResponseEntity<Group> findOne(@PathVariable Integer groupId) {
        return ResponseEntity.ok(groupService.findOne(groupId));
    }

    @ApiOperation(value = "get students in group by id")
    @GetMapping("{groupId}/students")
    public ResponseEntity<List<Student>> findAllByGroupId(@PathVariable Integer groupId) {
        return ResponseEntity.ok(studentService.findStudentsByGroup(groupId));
    }

    @ApiOperation(value = "add new group")
    @PostMapping()
    public ResponseEntity<Group> create(@Valid @RequestBody Group group) {
        return new ResponseEntity<Group>(groupService.create(group), HttpStatus.CREATED);
    }

    @ApiOperation(value = "update group")
    @PutMapping()
    public ResponseEntity<Group> update(@Valid @RequestBody Group group) {
        return ResponseEntity.ok(groupService.update(group));
    }

    @ApiOperation(value = "delete group by id")
    @DeleteMapping("{groupId}")
    public ResponseEntity<String> delete(@PathVariable Integer groupId) {
        groupService.delete(groupId);
        return ResponseEntity.ok("Delete group with id = " + groupId);
    }

}
