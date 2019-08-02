package ua.com.foxminded.webuniversity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.webuniversity.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByGroupId(Integer groupId);

}
