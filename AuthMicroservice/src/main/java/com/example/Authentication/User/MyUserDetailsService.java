package com.example.Authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDetails loadUserByUsername(String userName){
        String encodedPassword = passwordEncoder.encode("password");
        return new User("user",encodedPassword, Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
    }
}
