package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    TeacherRepository teacherRepository;

    @Test
    void findAllTest() {
        // GIVEN
        List<Teacher> teachers = new ArrayList<>();
        when(teacherRepository.findAll()).thenReturn(teachers);

        // WHEN
        TeacherService teacherService = new TeacherService(teacherRepository);
        List<Teacher> teachersFound = teacherService.findAll();

        // THEN
        assertEquals(teachers, teachersFound);
    }

    @Test
    void findByIdTest() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        // WHEN
        TeacherService teacherService = new TeacherService(teacherRepository);
        Teacher teacherFound = teacherService.findById(teacher.getId());

        // THEN
        assertEquals(teacher, teacherFound);
    }
}
