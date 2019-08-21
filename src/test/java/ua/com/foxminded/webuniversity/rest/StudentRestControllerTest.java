package ua.com.foxminded.webuniversity.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.webuniversity.WebuniversityApplication;
import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Student;
import ua.com.foxminded.webuniversity.exception.EntityNotFoundException;
import ua.com.foxminded.webuniversity.exception.StudentsLimitExceededException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebuniversityApplication.class)
@AutoConfigureMockMvc
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void findAllShouldReturnStudentListWhenRequestIsOk() throws Exception {
        when(studentRepository.findAll()).thenReturn(createStudentList());
        mockMvc.perform(get("/students"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("First"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Second"))
            .andExpect(jsonPath("$[2].id").value(3))
            .andExpect(jsonPath("$[2].name").value("Third"));
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void findOneShouldReturnCorrectResponseWhenStudentFound() throws Exception {
        Student student = createStudentList().get(0);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        mockMvc.perform(get("/students/1"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("First"));
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    public void findOneShouldReturnNotFoundStatusWhenStudentNotFound() throws Exception {
        when(studentRepository.findById(5)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/students/5"))
            .andExpect(status().isNotFound());
        verify(studentRepository, times(1)).findById(5);
    }

    @Test
    public void createShouldReturnCreatedStatusWhenRequestIsOk() throws Exception {
        Student newStudent = createStudentList().get(1);
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);
        mockMvc.perform(post("/students").content(objectMapper.writeValueAsString(newStudent)).contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Second"));
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Ignore
    public void updateShouldReturnCorrectResponseWhenRequestIsOk() throws Exception {
        Student updateStudent = createStudentList().get(2);
        when(studentRepository.save(any(Student.class))).thenReturn(updateStudent);
        mockMvc.perform(put("/students").content(objectMapper.writeValueAsString(updateStudent)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("Third"));
        verify(studentRepository, times(1)).save(updateStudent);
    }

    @Test
    public void updateShouldReturnBadRequestStatusWhenValidationFailed() throws Exception {
        Student updateStudent = new Student("N");
        updateStudent.setId(4);
        when(studentRepository.save(updateStudent)).thenThrow(StudentsLimitExceededException.class);
        mockMvc.perform(put("/students").content(objectMapper.writeValueAsString(updateStudent)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldSuccessRemoveWhenStudentFound() throws Exception {
        Student student = createStudentList().get(0);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        mockMvc.perform(delete("/students/1"))
            .andExpect(status().isOk());
        verify(studentRepository, times(1)).deleteById(1);
    }

    private List<Student> createStudentList() {
        Student student1 = new Student("First");
        student1.setId(1);
        Student student2 = new Student("Second");
        student2.setId(2);
        Student student3 = new Student("Third");
        student3.setId(3);
        return Stream.of(student1, student2, student3).collect(Collectors.toList());
    }

}
