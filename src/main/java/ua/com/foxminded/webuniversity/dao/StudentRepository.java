package ua.com.foxminded.webuniversity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.foxminded.webuniversity.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "select * from student where group_id = ?1", nativeQuery = true)
    List<Student> findStudentsByGroup(Integer groupId);

}
