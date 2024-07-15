package com.gl.website.services.impl;

import com.gl.website.exceptions.ResourceNotFoundException;
import com.gl.website.models.User;
import com.gl.website.payload.LoginResponse;
import com.gl.website.repositories.UserRepository;
import com.gl.website.services.UserService;
import com.gl.website.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User muser) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(muser.getName());
        user.setAge(muser.getAge());
        user.setEmail(muser.getEmail());
        user.setPassword(passwordEncoder.encode(muser.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(String email, String password) {

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            User authenicateUser = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
            LoginResponse response = null;
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new ResourceNotFoundException("Invalid email or password!!");
            }else if (authenicateUser!=null){

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );

                UserDetails userDetails1 = (UserDetails) authentication.getPrincipal();
                String jwt = jwtUtil.generateToken(userDetails1.getUsername());

                response = new LoginResponse();
                response.setUserId(authenicateUser.getId());
                response.setEmail(authenicateUser.getEmail());
                response.setName(authenicateUser.getName());
                response.setToken(jwt);
            }

            return response;

        } catch (AuthenticationException e) {
            throw new ResourceNotFoundException("Invalid email or password!!");
        }
    }
}
