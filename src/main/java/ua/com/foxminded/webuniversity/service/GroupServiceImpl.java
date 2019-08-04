package ua.com.foxminded.webuniversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.webuniversity.dao.GroupRepository;
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group create(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }

    public Group findOne(Integer id) {
        Optional<Group> result = groupRepository.findById(id);
        Group group = null;
        if (result.isPresent()) {
            group = result.get();
        } else {
            throw new EntityNotFoundException("Cannot find group id = " + id);
        }
        return group;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public void delete(Integer id) {
        Optional<Group> result = groupRepository.findById(id);
        if (result.isPresent()) {
            groupRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cannot find group id = " + id);
        }
    }

}
