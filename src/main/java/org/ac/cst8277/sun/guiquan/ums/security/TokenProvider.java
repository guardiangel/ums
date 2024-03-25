package org.ac.cst8277.sun.guiquan.ums.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {
    String generateToken(UserDetails userDetails);
}
