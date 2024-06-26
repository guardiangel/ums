package org.ac.cst8277.sun.guiquan.ums.services;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource(name = "userManagementService")
    private UserManagementService userManagementService;

    @Resource(name = "userTokenRepository")
    private UserTokenRepository userTokenRepository;

    @Test
    public void getAllUsers() {
        List<UserEntity> userVoList
                = userManagementService.getAllUser();
        userVoList.forEach(usersEntity -> {
            System.err.println(usersEntity);
        });
    }

    @Test
    public void getUserTokenByTokenId() {
        UserTokenEntity userTokenEntity
                = userManagementService
                .getUserTokenByTokenId("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJHdWlxdWFuIFN1biIsInJvbGVzIjpbIlNVQlNDUklCRVIiXSwiaWF0IjoxNzExNDA1MTEwLCJleHAiOjE3MTE0MDYwMTB9.ZaL2QjWIe83TCOEgbMcQVWNIN_cxiRAjwej6UmemJGg");
        if (userTokenEntity != null) {
            System.err.println(userTokenEntity);
        }
    }
}
