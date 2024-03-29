package ua.com.foxminded.webuniversity.service;

import java.util.List;

import ua.com.foxminded.webuniversity.entity.Group;

public interface GroupService {

    Group create(Group group);

    Group update(Group group);

    Group findOne(Integer id);

    List<Group> findAll();

    void delete(Integer id);

}
