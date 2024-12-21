package com.principal.pruebaspringbootjwt;

import com.principal.pruebaspringbootjwt.controller.UserController;
import com.principal.pruebaspringbootjwt.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void testGetAllUsers() {
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        List<UserDTO> users = response.getBody();
        assert users != null;
        assertEquals(2, users.size());
        assertEquals("roberto@hotmail.com", users.getLast().getEmail());
        assertEquals("susana@hotmail.com", users.getFirst().getEmail());
    }

}
