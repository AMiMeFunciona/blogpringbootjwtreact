package com.principal.pruebaspringbootjwt;

import com.principal.pruebaspringbootjwt.dto.UserDTO;
import com.principal.pruebaspringbootjwt.model.User;
import com.principal.pruebaspringbootjwt.repository.UserRepository;
import com.principal.pruebaspringbootjwt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetAllUsers() {

        User userPrueba = new User();
        userPrueba.setId(UUID.fromString("a642f64e-d364-4e95-a1a7-776bbd0cfafd"));
        userPrueba.setEmail("roberto@hotmail.com");

        List<User> mockUsers = List.of(userPrueba);
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserDTO> userDTOs = userService.getAllUser();

        assertEquals(1, userDTOs.size());
        assertEquals("roberto@hotmail.com", userDTOs.getFirst().getEmail());
        verify(userRepository, times(1)).findAll();
    }

}
