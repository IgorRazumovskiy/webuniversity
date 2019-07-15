package ua.com.foxminded.webuniversity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.webuniversity.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
