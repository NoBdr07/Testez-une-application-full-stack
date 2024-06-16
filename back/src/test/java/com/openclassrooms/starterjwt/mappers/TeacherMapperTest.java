package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void TeacherDtoToEntityTest() {
        // GIVEN
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        // WHEN
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // THEN
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());

    }

    @Test
    public void TeacherEntityToDtoTest() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        // WHEN
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // THEN
        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
    }

    @Test
    public void TeacherListEntityToDtoTest() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);

        // WHEN
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // THEN
        assertEquals(teachers.get(0).getId(), teacherDtos.get(0).getId());
        assertEquals(teachers.get(0).getFirstName(), teacherDtos.get(0).getFirstName());
        assertEquals(teachers.get(0).getLastName(), teacherDtos.get(0).getLastName());
    }

    @Test
    public void TeacherListDtoToEntityTest() {
        // GIVEN
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        List<TeacherDto> teacherDtos = new ArrayList<>();
        teacherDtos.add(teacherDto);

        // WHEN
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // THEN
        assertEquals(teacherDtos.get(0).getId(), teachers.get(0).getId());
        assertEquals(teacherDtos.get(0).getFirstName(), teachers.get(0).getFirstName());
        assertEquals(teacherDtos.get(0).getLastName(), teachers.get(0).getLastName());
    }

    @Test
    public void TeacherDtoToEntityNullTest() {
        // GIVEN
        TeacherDto teacherDto = new TeacherDto();
        teacherDto = null ;

        // WHEN
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // THEN
        assertEquals(teacher, null);
    }

    @Test
    public void TeacherEntityToDtoNullTest() {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher = null ;

        // WHEN
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // THEN
        assertEquals(teacherDto, null);
    }

    @Test
    public void TeacherListDtoToEntityNullTest() {
        // GIVEN
        List<TeacherDto> teacherDtos = new ArrayList<>();
        teacherDtos = null ;

        // WHEN
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // THEN
        assertEquals(teachers, null);
    }

    @Test
    public void TeacherListEntityToDtoNullTest() {
        // GIVEN
        List<Teacher> teachers = new ArrayList<>();
        teachers = null ;

        // WHEN
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // THEN
        assertEquals(teacherDtos, null);
    }

}
