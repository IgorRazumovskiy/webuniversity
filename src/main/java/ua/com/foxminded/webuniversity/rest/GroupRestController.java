package ua.com.foxminded.webuniversity.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.webuniversity.dao.EntryMissingException;
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.service.GroupService;

@RestController
@RequestMapping("/university/groups")
public class GroupRestController {
    
    private GroupService groupService;

    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("{groupId}")
    public Group findOne(@PathVariable Integer groupId) {
        return groupService.findOne(groupId);
    }

    @PostMapping()
    public Group create(@RequestBody Group group) {
        return groupService.create(group);
    }

    @PutMapping()
    public Group update(@RequestBody Group group) {
        return groupService.update(group);
    }

    @DeleteMapping("{groupId}")
    public String delete(@PathVariable Integer groupId) {
        Group group = groupService.findOne(groupId);
        if (group == null) {
            throw new EntryMissingException("Cannot find group id = " + groupId);
        }
        groupService.delete(groupId);
        return "Delete group with id = " + groupId;
    }

}
