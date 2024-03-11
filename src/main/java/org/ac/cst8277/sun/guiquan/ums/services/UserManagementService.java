package org.ac.cst8277.sun.guiquan.ums.services;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.repositories.RoleRepository;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserManagementRepository;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserTokenRepository;
import org.ac.cst8277.sun.guiquan.ums.requestvo.UserInputVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service("userManagementService")
public class UserManagementService {
    @Resource(name = "userManagementRepository")
    private UserManagementRepository userManagementRepository;

    @Resource(name = "userTokenRepository")
    private UserTokenRepository userTokenRepository;

    @Resource(name = "roleRepository")
    private RoleRepository roleRepository;

    public List<UserEntity> getAllUser() {
        List<UserEntity> userEntities = userManagementRepository.getAllUser();
        return userEntities;
    }

    public List<RoleEntity> getAllRoles() {
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepository.findAll();
        return roleEntities;
    }

    /**
     * get user token. If not exist, create it.
     *
     * @param userId
     * @return
     */
    public UserTokenEntity generateTokenByUserId(String userId) {
        Optional<UserEntity> userEntity = userManagementRepository.findById(userId);
        //if user does not exist, throw exception
        if (!userEntity.isPresent()) {
            throw new RuntimeException("Can't find the user by the current userId");
        }
        UserTokenEntity userTokenEntity = userTokenRepository.getTokenById(userId);
        if (ObjectUtils.isEmpty(userTokenEntity)) {
            userTokenEntity = new UserTokenEntity(userId,
                    UUID.randomUUID().toString());
            userTokenRepository.save(userTokenEntity);
        }
        return userTokenEntity;
    }

    public boolean verifyToken(String token) {
        UserTokenEntity userTokenEntity = userTokenRepository.getUserTokenEntityByToken(token);
        return userTokenEntity != null;
    }

    public UserEntity saveCascade(UserInputVo userInputVo) {

        UserEntity userEntity = new UserEntity();
        Set<RoleEntity> roleEntities = new HashSet<>();
        BeanUtils.copyProperties(userInputVo, userEntity);

        /*userInputVo.getRoles().forEach(roleInputVo -> {
            RoleEntity roleEntity = new RoleEntity();
            BeanUtils.copyProperties(roleInputVo, roleEntity);
            roleEntities.add(roleEntity);
        });

        userEntity.setRoles(roleEntities);*/
        try {
            userEntity = userManagementRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userEntity;
    }

    public boolean deleteCascade(UserInputVo userInputVo) {
        Optional<UserEntity> userEntityOptional = userManagementRepository.findById(userInputVo.getId());
        if (userEntityOptional.isPresent()) {
            userManagementRepository.delete(userEntityOptional.get());
            return true;
        } else {
            throw new RuntimeException("Can't find the user with id:" + userInputVo.getId());
        }
    }
}
