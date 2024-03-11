package org.ac.cst8277.sun.guiquan.ums.controllers;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.services.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class UserManagementController {

    @Resource(name = "userManagementService")
    private UserManagementService userManagementService;

    @GetMapping("/generateTokenByUserId")
    public ResponseEntity<UserTokenEntity> generateTokenByUserId(@RequestParam("userId") String userId) {
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        try {
            userTokenEntity = userManagementService.generateTokenByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userTokenEntity);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers(@RequestHeader("token") String token) {
        System.err.println("token=" + token);
        boolean flag = userManagementService.verifyToken(token);
        if (flag) {
            List<UserEntity> userEntities = userManagementService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK).body(userEntities);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "The current token is not valid.");
        }
    }

}
