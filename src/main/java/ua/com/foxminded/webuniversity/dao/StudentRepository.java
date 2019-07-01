package ua.com.foxminded.webuniversity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.webuniversity.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
