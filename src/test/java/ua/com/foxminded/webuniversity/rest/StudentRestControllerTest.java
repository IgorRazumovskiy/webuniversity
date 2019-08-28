package ua.com.foxminded.webuniversity.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;

import ua.com.foxminded.webuniversity.dao.StudentRepository;
import ua.com.foxminded.webuniversity.entity.Group;
import ua.com.foxminded.webuniversity.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@DBRider
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DataSet("students.yml")
    public void findAllShouldReturnStudentListWhenRequestIsOk() throws Exception {
        mockMvc.perform(get("/students"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(3);
    }

    @Test
    public void findOneShouldReturnCorrectResponseWhenStudentFound() throws Exception {
        Integer studentId = 4;
        mockMvc.perform(get("/students/{studentId}", studentId))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(studentId))
            .andExpect(jsonPath("$.name").value("Найджел"))
            .andDo(print());
    }

    @Test
    public void findOneShouldReturnNotFoundStatusWhenStudentNotFound() throws Exception {
        Integer studentId = 30;
        mockMvc.perform(get("/students/{studentId}", studentId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Cannot find student id = " + studentId))
            .andDo(print());
    }

    @Test
    public void createShouldReturnCreatedStatusWhenRequestIsOk() throws Exception {
        Student newStudent = new Student("First");
        newStudent.setId(1);
        mockMvc.perform(post("/students")
            .content(objectMapper.writeValueAsString(newStudent))
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(newStudent.getName()))
            .andDo(print());
    }
    
    @Test
    public void createShouldReturnBadRequestStatusWhenStudentNotValid() throws Exception {
        Student newStudent = new Student("F");
        newStudent.setId(1);
        mockMvc.perform(post("/students")
            .content(objectMapper.writeValueAsString(newStudent))
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    public void updateShouldReturnCorrectResponseWhenRequestIsOk() throws Exception {
        Student updateStudent = studentRepository.findById(1).get();
        mockMvc.perform(put("/students")
            .content(objectMapper.writeValueAsString(updateStudent))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void updateShouldReturnBadRequestStatusWhenValidationFailed() throws Exception {
        Student updateStudent = new Student("First");
        updateStudent.setId(1);
        Group group = new Group();
        group.setId(1);
        group.setMaxNumberOfStudents(1000);
        updateStudent.setGroup(group);        
        mockMvc.perform(put("/students")
            .content(objectMapper.writeValueAsString(updateStudent))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    public void deleteShouldSuccessRemoveWhenStudentFound() throws Exception {
        Integer studentId = 20;
        mockMvc.perform(delete("/students/{studentId}", studentId))
            .andExpect(status().isOk())
            .andExpect(content().string("Delete student with id = " + studentId))
            .andDo(print());
    }
    
    @Test
    public void deleteShouldNotFoundStatusWhenStudentNotFound() throws Exception {
        Integer studentId = 31;
        mockMvc.perform(delete("/students/{studentId}", studentId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Cannot find student id = " + studentId))
            .andDo(print());
    }

}
