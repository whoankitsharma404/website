package com.gl.website.services;

import com.gl.website.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    User loginUser(String email,String password);
}

