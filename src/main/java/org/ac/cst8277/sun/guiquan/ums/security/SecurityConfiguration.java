package org.ac.cst8277.sun.guiquan.ums.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                     ReactiveAuthenticationManager authenticationManager,
                                                     ServerAuthenticationConverter authenticationConverter) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                               "/",
                                "/error",
                                "/login/**",
                                "/oauth2/**",
                                "/webjars/**",
                                "/generateToken").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .oauth2Login(ServerHttpSecurity.OAuth2LoginSpec::getClass)
                .oauth2Client(ServerHttpSecurity.OAuth2ClientSpec::getClass)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        var user = User.builder()
                .username("Guiquan Sun")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }


}
