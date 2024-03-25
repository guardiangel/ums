package org.ac.cst8277.sun.guiquan.ums.services;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.ac.cst8277.sun.guiquan.ums.exceptions.CustomException;
import org.ac.cst8277.sun.guiquan.ums.repositories.RoleRepository;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserManagementRepository;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserTokenRepository;
import org.ac.cst8277.sun.guiquan.ums.requestvo.RoleInputVo;
import org.ac.cst8277.sun.guiquan.ums.requestvo.UserInputVo;
import org.ac.cst8277.sun.guiquan.ums.security.CustomUserService;
import org.ac.cst8277.sun.guiquan.ums.security.TokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service("userManagementService")
@RequiredArgsConstructor
public class UserManagementService {

    @Value("${jwt.token-expiration-seconds}")
    private long tokenExpiration;
    @Resource(name = "userManagementRepository")
    private UserManagementRepository userManagementRepository;

    @Resource(name = "userTokenRepository")
    private UserTokenRepository userTokenRepository;

    @Resource(name = "roleRepository")
    private RoleRepository roleRepository;

    private final CustomUserService userDetailsService;

    private final TokenProvider tokenProvider;

    public List<UserEntity> getAllUser() {
        List<UserEntity> userEntities = userManagementRepository.getAllUser();
        return userEntities;
    }

    public List<RoleEntity> getAllRoles() {
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepository.findAll();
        return roleEntities;
    }

    public UserEntity saveUserCascade(UserInputVo userInputVo) {

        final UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userInputVo, userEntity);


        userInputVo.getRoles().forEach((RoleInputVo roleInputVo) -> {
            Optional<RoleEntity> roleEntityOptional
                    = roleRepository.findById(roleInputVo.getId());
            if (roleEntityOptional.isPresent()) {
                userEntity.getRoles().add(roleEntityOptional.get());
            }
        });
        UserEntity result;
        try {
            result = userManagementRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
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

    public UserTokenEntity getUserTokenByTokenId(String token) {

        UserTokenEntity userTokenEntity
                = userTokenRepository.getUserTokenEntityByToken(token);

        if (userTokenEntity != null) {
            Long currentTimeStamp = new Date().getTime();
            if (currentTimeStamp - userTokenEntity.getIssueAt()
                    > userTokenEntity.getDuration()) {
                throw new CustomException(HttpStatus.UNAUTHORIZED.value(),
                        "The current toke has expired, please get new one from ums application.");
            }

        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(),
                    "Invalid token, can't find a user based on the current token");
        }

        return userTokenEntity;
    }

    public String generateTokenByUsername(String name) {

        String token = null;
        try {
            //get token from table first
            UserEntity userEntity = userManagementRepository.findUserEntityByName(name);
            if (userEntity == null) {
                throw new RuntimeException("Can't find a user in the database with name:" + name);
            }
            UserTokenEntity existUserTokenEntity
                    = userTokenRepository.getTokenById(userEntity.getId());
            //if token is not null and still valid
            if (existUserTokenEntity != null
                    && (new Date().getTime() - existUserTokenEntity.getIssueAt()
                    < existUserTokenEntity.getDuration())) {
                token = existUserTokenEntity.getToken();
            } else {
                Mono<UserDetails> userDetailsMono = userDetailsService.findByUsername(name);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future future = executorService.submit(() -> userDetailsMono.block());
                UserDetails userDetails = (UserDetails) future.get();
                token = tokenProvider.generateToken(userDetails);
                //Save generated token to database
                UserTokenEntity userTokenEntity = new UserTokenEntity(userEntity.getId(),
                        token, tokenExpiration, new Date().getTime());
                userTokenRepository.save(userTokenEntity);
            }
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            token = e.getMessage();
        }
        return token;
    }
}
