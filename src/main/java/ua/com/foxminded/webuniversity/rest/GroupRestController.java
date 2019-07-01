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
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.service.GroupService;

@RestController
@RequestMapping("/webuniversity")
public class GroupRestController {
    private GroupService groupService;

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/groups/{groupId}")
    public Group findOne(@PathVariable Integer groupId) {
        return groupService.findOne(groupId);
    }

    @PostMapping("/groups")
    public Group create(@RequestBody Group group) {
        return groupService.create(group);
    }

    @PutMapping("/groups")
    public Group update(@RequestBody Group group) {
        return groupService.update(group);
    }

    @DeleteMapping("/groups/{groupId}")
    public String delete(@PathVariable Integer groupId) {
        Group group = groupService.findOne(groupId);
        if (group == null) {
            throw new DAOException("Cannot find group id = " + groupId);
        }
        groupService.delete(groupId);
        return "Delete group with id = " + groupId;
    }

}
