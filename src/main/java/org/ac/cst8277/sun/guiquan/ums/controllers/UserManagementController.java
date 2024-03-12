package org.ac.cst8277.sun.guiquan.ums.controllers;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.requestvo.UserInputVo;
import org.ac.cst8277.sun.guiquan.ums.services.UserManagementService;
import org.ac.cst8277.sun.guiquan.ums.utils.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
public class UserManagementController {

    @Resource(name = "userManagementService")
    private UserManagementService userManagementService;

    @Resource(name = "jsonResult")
    private JSONResult jsonResult;

    @GetMapping("/generateTokenByUserId")
    public JSONResult<Object> generateTokenByUserId(@RequestParam("userId") String userId) {
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        try {
            userTokenEntity = userManagementService.generateTokenByUserId(userId);
            return jsonResult.success(HttpStatus.OK.value(), userTokenEntity);
        } catch (RuntimeException e) {
            return jsonResult.error(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    @GetMapping("/getUserTokenByTokenId")
    public JSONResult<Object> getUserByToken(@RequestHeader("token") String token) {
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        try {
            userTokenEntity = userManagementService.getUserTokenByTokenId(token);
            return jsonResult.success(HttpStatus.OK.value(), userTokenEntity);
        } catch (RuntimeException e) {
            return jsonResult.error(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }


    @GetMapping("/getAllUsers")
    public JSONResult<Object> getAllUsers(@RequestHeader("token") String token) {
        boolean flag = userManagementService.verifyToken(token);
        if (flag) {
            List<UserEntity> userEntities = userManagementService.getAllUser();
            return jsonResult.success(HttpStatus.OK.value(), userEntities);
        } else {
            return jsonResult.error(HttpStatus.UNAUTHORIZED.value(),
                    "The current token is not valid.");
        }
    }

    @GetMapping("/getAllRoles")
    public JSONResult<Object> getAllRoles(@RequestHeader("token") String token) {
        boolean flag = userManagementService.verifyToken(token);
        if (flag) {
            List<RoleEntity> userEntities = userManagementService.getAllRoles();
            return jsonResult.success(HttpStatus.OK.value(), userEntities);
        } else {
            return jsonResult.error(HttpStatus.UNAUTHORIZED.value(),
                    "The current token is not valid.");
        }
    }

    @PostMapping("/addNewUser")
    public JSONResult<Object> addNewUser(@RequestHeader("token") String token,
                                         @RequestBody UserInputVo userInputVo) {

        userInputVo.setCreated(new Date().getTime());
        boolean flag = userManagementService.verifyToken(token);
        if (flag) {
            try {
                UserEntity userEntity
                        = userManagementService.saveUserCascade(userInputVo);
                return jsonResult.success(HttpStatus.OK.value(),
                        "Add a new user successfully.", userEntity);
            } catch (RuntimeException e) {
                return jsonResult.error(HttpStatus.BAD_GATEWAY.value(),
                        e.getMessage());
            }
        } else {
            return jsonResult.error(HttpStatus.UNAUTHORIZED.value(),
                    "The current token is not valid. can't add a new user.");
        }
    }

    @DeleteMapping("/deleteUser")
    public JSONResult<Object> deleteUser(@RequestHeader("token") String token,
                                         @RequestBody UserInputVo userInputVo) {

        boolean flag = userManagementService.verifyToken(token);
        if (flag) {
            try {
                userManagementService.deleteCascade(userInputVo);
                return jsonResult.success(HttpStatus.OK.value(),
                        "Delete the user with id " + userInputVo.getId() + " successfully.");
            } catch (RuntimeException e) {
                return jsonResult.error(HttpStatus.FAILED_DEPENDENCY.value(),
                        "Can't delete the user, the reason is:" + e.getMessage());
            }

        } else {
            return jsonResult.error(HttpStatus.UNAUTHORIZED.value(),
                    "The current token is not valid. can't add a new user.");
        }
    }
}
