package ua.com.foxminded.webuniversity.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;

class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createShouldReturnStudentWhenStudentSaved() {
        Student student = new Student("First");
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(student, studentService.create(student));
    }

    @Test
    void findOneShouldReturnStudentWhenStudentFound() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        assertEquals(student, studentService.findOne(1));
        assertNotNull(studentService.findOne(1));
    }

    @Test
    void findOneShouldReturnExceptionWhenStudentNotFound() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.findOne(null);
        });
    }

    @Test
    void findAllShouldReturnListWithAllStudents() {
        when(studentRepository.findAll()).thenReturn(createStudentList());
        assertEquals(2, studentService.findAll().size());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoStudents() {
        when(studentRepository.findByGroupId(anyInt())).thenReturn(createStudentList().subList(0, 0));
        assertEquals(0, studentService.findAll().size());
    }

    @Test
    void deleteShouldCallMethodOneTimeWhenStudentFound() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        studentService.delete(1);
        verify(studentRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteShouldReturnExceptionWhenStudentNotFound() {
        Student student = new Student("First");
        student.setId(1);
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.delete(1);
        });
    }

    @Test
    void findStudentsByGroupShouldReturnListWhenStudentInGroup() {
        when(studentRepository.findByGroupId(anyInt())).thenReturn(createStudentList().subList(0, 1));
        assertEquals(1, studentService.findStudentsByGroup(3).size());
    }

    private List<Student> createStudentList() {
        Student student = new Student("First");
        student.setId(1);
        Student student2 = new Student("Second");
        student.setId(2);
        return Stream.of(student, student2).collect(Collectors.toList());
    }

}
