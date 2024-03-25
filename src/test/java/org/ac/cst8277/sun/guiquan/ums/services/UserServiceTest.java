package org.ac.cst8277.sun.guiquan.ums.services;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource(name = "userManagementService")
    private UserManagementService userManagementService;

    @Test
    public void getAllUsers() {
        List<UserEntity> userVoList
                = userManagementService.getAllUser();
        userVoList.forEach(usersEntity -> {
            System.err.println(usersEntity);
        });
    }
}
