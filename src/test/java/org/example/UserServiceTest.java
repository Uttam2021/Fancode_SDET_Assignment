package org.example;

import org.example.service.UserService;
import org.testng.annotations.Test;

public class UserServiceTest {

    @Test
    public void testValidateFanCodeUsers() {
        UserService userService = new UserService();
        userService.validateFanCodeUsers();
    }
}
