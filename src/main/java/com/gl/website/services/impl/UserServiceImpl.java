package com.gl.website.services.impl;

import com.gl.website.models.User;
import com.gl.website.repositories.UserRepository;
import com.gl.website.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email,String password) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
