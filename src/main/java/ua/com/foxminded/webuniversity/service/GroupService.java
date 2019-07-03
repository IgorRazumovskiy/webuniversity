package ua.com.foxminded.webuniversity.service;

import java.util.List;

import ua.com.foxminded.webuniversity.entity.Group;

public interface GroupService {

    public Group create(Group group);

    public Group update(Group group);

    public Group findOne(Integer id);

    public List<Group> findAll();

    public void delete(Integer id);

}
