package com.gl.website.services;

import com.gl.website.models.User;
import com.gl.website.payload.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    LoginResponse loginUser(String email, String password);
}

