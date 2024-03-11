package org.ac.cst8277.sun.guiquan.ums.services;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserManagementRepository;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userManagementService")
public class UserManagementService {
    @Resource(name = "userManagementRepository")
    private UserManagementRepository userManagementRepository;

    @Resource(name = "userTokenRepository")
    private UserTokenRepository userTokenRepository;

    public List<UserEntity> getAllUser() {
        List<UserEntity> userEntities = userManagementRepository.getAllUser();
        return userEntities;
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

}
