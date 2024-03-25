package org.ac.cst8277.sun.guiquan.ums.controllers;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.requestvo.UserInputVo;
import org.ac.cst8277.sun.guiquan.ums.services.UserManagementService;
import org.ac.cst8277.sun.guiquan.ums.utils.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


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

    /**
     * principal=Name: [3304152], Granted Authorities: [[OAUTH2_USER, SCOPE_read:user]], User Attributes: [{login=guardiangel, id=3304152, node_id=MDQ6VXNlcjMzMDQxNTI=, avatar_url=https://avatars.githubusercontent.com/u/3304152?v=4, gravatar_id=, url=https://api.github.com/users/guardiangel, html_url=https://github.com/guardiangel, followers_url=https://api.github.com/users/guardiangel/followers, following_url=https://api.github.com/users/guardiangel/following{/other_user}, gists_url=https://api.github.com/users/guardiangel/gists{/gist_id}, starred_url=https://api.github.com/users/guardiangel/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/guardiangel/subscriptions, organizations_url=https://api.github.com/users/guardiangel/orgs, repos_url=https://api.github.com/users/guardiangel/repos, events_url=https://api.github.com/users/guardiangel/events{/privacy}, received_events_url=https://api.github.com/users/guardiangel/received_events, type=User, site_admin=false, name=Guiquan Sun, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=10, public_gists=0, followers=0, following=0, created_at=2013-01-18T08:00:41Z, updated_at=2024-03-25T00:18:04Z, private_gists=7, total_private_repos=19, owned_private_repos=19, disk_usage=154224, collaborators=7, two_factor_authentication=false, plan={name=free, space=976562499, collaborators=0, private_repos=10000}}]
     * @param principal
     * @return
     */
    @GetMapping("/user")
    public Map<String, Object> login(@AuthenticationPrincipal OAuth2User principal) {

        String name = principal.getAttribute("name");

        System.err.println("principal="+principal);
        System.err.println("name="+name);

        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}
