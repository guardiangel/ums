package org.ac.cst8277.sun.guiquan.ums.security;

import jakarta.annotation.Resource;
import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.ac.cst8277.sun.guiquan.ums.repositories.UserManagementRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service("cumtomerUserService")
public class CumtomerUserService implements ReactiveUserDetailsService {

    @Resource(name = "userManagementRepository")
    private UserManagementRepository userManagementRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> {
            UserEntity userEntity = userManagementRepository.findUserEntityByName(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            List<GrantedAuthority> authorities
                    = new ArrayList<>();

            for (RoleEntity roleEntity : userEntity.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
            }
            return new User(
                    userEntity.getName(),
                    "",//set password to empty string
                    authorities
            );
        });
    }
}
