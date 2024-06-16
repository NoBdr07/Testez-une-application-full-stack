package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void UserDtoToEntityTest() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@mail.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setPassword("password");
        userDto.setAdmin(true);

        // WHEN
        User user = userMapper.toEntity(userDto);

        // THEN
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.isAdmin(), user.isAdmin());
    }

    @Test
    public void UserEntityToDtoTest() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setPassword("password");
        user.setAdmin(true);

        // WHEN
        UserDto userDto = userMapper.toDto(user);

        // THEN
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.isAdmin(), userDto.isAdmin());
    }

    @Test
    public void UserListDtoToEntityTest() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@mail.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setPassword("password");
        userDto.setAdmin(true);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);

        // WHEN
        List<User> users = userMapper.toEntity(userDtos);

        // THEN
        assertEquals(userDtos.get(0).getId(), users.get(0).getId());
        assertEquals(userDtos.get(0).getEmail(), users.get(0).getEmail());
        assertEquals(userDtos.get(0).getLastName(), users.get(0).getLastName());
        assertEquals(userDtos.get(0).getFirstName(), users.get(0).getFirstName());
        assertEquals(userDtos.get(0).getPassword(), users.get(0).getPassword());
        assertEquals(userDtos.get(0).isAdmin(), users.get(0).isAdmin());
    }

    @Test
    public void UserListEntityToDtoTest() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setPassword("password");
        user.setAdmin(true);

        List<User> users = new ArrayList<>();
        users.add(user);

        // WHEN
        List<UserDto> userDtos = userMapper.toDto(users);

        // THEN
        assertEquals(users.get(0).getId(), userDtos.get(0).getId());
        assertEquals(users.get(0).getEmail(), userDtos.get(0).getEmail());
        assertEquals(users.get(0).getLastName(), userDtos.get(0).getLastName());
        assertEquals(users.get(0).getFirstName(), userDtos.get(0).getFirstName());
        assertEquals(users.get(0).getPassword(), userDtos.get(0).getPassword());
        assertEquals(users.get(0).isAdmin(), userDtos.get(0).isAdmin());
    }


    @Test
    public void SessionDtoToEntityNullTest() {
        // GIVEN
        UserDto userDto = new UserDto();
        userDto = null ;

        // WHEN
        User user = userMapper.toEntity(userDto);

        // THEN
        assertEquals(user, null);
    }

    @Test
    public void SessionEntityToDtoNullTest() {
        // GIVEN
        User user = new User();
        user = null ;

        // WHEN
        UserDto userDto = userMapper.toDto(user);

        // THEN
        assertEquals(userDto, null);
    }

    @Test
    public void TeacherListDtoToEntityNullTest() {
        // GIVEN
        List<UserDto> userDtos = new ArrayList<>();
        userDtos = null ;

        // WHEN
        List<User> users = userMapper.toEntity(userDtos);

        // THEN
        assertEquals(users, null);
    }

    @Test
    public void TeacherListEntityToDtoNullTest() {
        // GIVEN
        List<User> users = new ArrayList<>();
        users = null ;

        // WHEN
        List<UserDto> userDtos = userMapper.toDto(users);

        // THEN
        assertEquals(userDtos, null);
    }
}
