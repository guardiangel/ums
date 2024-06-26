package org.ac.cst8277.sun.guiquan.ums.controllers;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.exceptions.CustomException;
import org.ac.cst8277.sun.guiquan.ums.requestvo.UserInputVo;
import org.ac.cst8277.sun.guiquan.ums.services.UserManagementService;
import org.ac.cst8277.sun.guiquan.ums.utils.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class UserManagementController {
    @Resource(name = "userManagementService")
    private UserManagementService userManagementService;
    @Resource(name = "jsonResult")
    private JSONResult jsonResult;


    /**
     * principal=Name: [3304152],
     * Granted Authorities: [[OAUTH2_USER, SCOPE_read:user]],
     * User Attributes: [{login=guardiangel, id=3304152, node_id=MDQ6VXNlcjMzMDQxNTI=, avatar_url=https://avatars.githubusercontent.com/u/3304152?v=4, gravatar_id=, url=https://api.github.com/users/guardiangel, html_url=https://github.com/guardiangel, followers_url=https://api.github.com/users/guardiangel/followers, following_url=https://api.github.com/users/guardiangel/following{/other_user}, gists_url=https://api.github.com/users/guardiangel/gists{/gist_id}, starred_url=https://api.github.com/users/guardiangel/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/guardiangel/subscriptions, organizations_url=https://api.github.com/users/guardiangel/orgs, repos_url=https://api.github.com/users/guardiangel/repos, events_url=https://api.github.com/users/guardiangel/events{/privacy}, received_events_url=https://api.github.com/users/guardiangel/received_events, type=User, site_admin=false, name=Guiquan Sun, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=10, public_gists=0, followers=0, following=0, created_at=2013-01-18T08:00:41Z, updated_at=2024-03-25T00:18:04Z, private_gists=7, total_private_repos=19, owned_private_repos=19, disk_usage=154224, collaborators=7, two_factor_authentication=false, plan={name=free, space=976562499, collaborators=0, private_repos=10000}}]
     *
     * @param principal
     * @return
     */
    @GetMapping("/generateToken")
    public Map<String, Object> generateToken(@AuthenticationPrincipal OAuth2User principal) {

        HashMap<String, Object> hashMap = new HashMap<>();

        if (principal != null) {
            String name = principal.getAttribute("name");
            String token = userManagementService.generateTokenByUsername(name);
            hashMap.put("name", token == null ? name : token);
        } else {
            hashMap.put("name", "NotLogin");
        }
        return hashMap;
    }

    @GetMapping("/getUserTokenByTokenId")
    public JSONResult<Object> getUserByToken(@RequestHeader("token") String token) {
        UserTokenEntity userTokenEntity = null;
        userTokenEntity = userManagementService.getUserTokenByTokenId(token);
        if (userTokenEntity != null) {
            return jsonResult.success(HttpStatus.OK.value(), userTokenEntity);
        } else {
            return jsonResult.success(HttpStatus.UNAUTHORIZED.value(), userTokenEntity);
        }
    }

    @GetMapping("/getAllUsers")
    public JSONResult<Object> getAllUsers() {
        List<UserEntity> userEntities = userManagementService.getAllUser();
        return jsonResult.success(HttpStatus.OK.value(), userEntities);
    }

    @GetMapping("/getAllRoles")
    public JSONResult<Object> getAllRoles() {
        List<RoleEntity> userEntities = userManagementService.getAllRoles();
        return jsonResult.success(HttpStatus.OK.value(), userEntities);
    }

    @PostMapping("/addNewUser")
    public JSONResult<Object> addNewUser(
            @RequestBody UserInputVo userInputVo) {
        userInputVo.setCreated(new Date().getTime());
        try {
            UserEntity userEntity
                    = userManagementService.saveUserCascade(userInputVo);
            return jsonResult.success(HttpStatus.OK.value(),
                    "Add a new user successfully.", userEntity);
        } catch (RuntimeException e) {
            return jsonResult.error(HttpStatus.BAD_GATEWAY.value(),
                    e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser")
    public JSONResult<Object> deleteUser(
            @RequestBody UserInputVo userInputVo) {
        try {
            userManagementService.deleteCascade(userInputVo);
            return jsonResult.success(HttpStatus.OK.value(),
                    "Delete the user with id " + userInputVo.getId() + " successfully.");
        } catch (RuntimeException e) {
            return jsonResult.error(HttpStatus.FAILED_DEPENDENCY.value(),
                    "Can't delete the user, the reason is:" + e.getMessage());
        }

    }


}
