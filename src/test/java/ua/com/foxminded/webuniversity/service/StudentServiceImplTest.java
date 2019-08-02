package ua.com.foxminded.webuniversity.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testCreate() {
        Student student = new Student("First");
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(student, studentService.create(student));
    }

    @Test
    void testFindOne() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        assertEquals(student, studentService.findOne(1));
        assertNotNull(studentService.findOne(1));
    }

    @Test
    void testFindOneWithException() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.findOne(1);
        });
    }

    @Test
    void testFindAll() {
        when(studentRepository.findAll())
                .thenReturn(Stream.of(new Student("First"), new Student("Second")).collect(Collectors.toList()));
        assertEquals(2, studentService.findAll().size());
    }

    @Test
    void testDelete() {
        Student student = new Student("First");
        student.setId(1);
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        studentService.delete(1);
        verify(studentRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindStudentsByGroup() {
        when(studentRepository.findByGroupId(anyInt()))
                .thenReturn(Stream.of(new Student("First")).collect(Collectors.toList()));
        assertEquals(1, studentService.findStudentsByGroup(3).size());
    }

}
