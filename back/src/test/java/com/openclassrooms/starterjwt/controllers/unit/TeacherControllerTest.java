package com.openclassrooms.starterjwt.controllers.unit;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {
    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherController teacherController;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTest() {
        // GIVEN
        long id = 1L;
        Teacher teacher = new Teacher();
        when(teacherService.findById(id)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(new TeacherDto());

        // WHEN
        ResponseEntity<?> responseEntity = teacherController.findById(String.valueOf(id));

        // THEN
        assertEquals(HttpStatus.OK , responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdBadRequestTest() {
        // GIVEN
        String id = "wrongId";

        // WHEN
        ResponseEntity<?> responseEntity = teacherController.findById(id);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdNotFoundTest() {
        // GIVEN
        long id = 0L;
        when(teacherService.findById(id)).thenReturn(null);

        // WHEN
        ResponseEntity<?> responseEntity = teacherController.findById(String.valueOf(id));

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllTest() {
        // GIVEN
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher());
        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(Arrays.asList(new TeacherDto()));

        // WHEN
        ResponseEntity<?> responseEntity = teacherController.findAll();

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
