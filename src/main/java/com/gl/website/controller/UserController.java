package com.gl.website.controller;

import com.gl.website.models.User;
import com.gl.website.payload.LoginResponse;
import com.gl.website.services.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/dashboard")
    public String i() {
        return "dashboard";
    }
    @GetMapping("/check")
    public String check() {
        return "check";
    }


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<User> registerUser(@RequestBody User muser,
                               Model model) {
        User user =userService.saveUser(muser);
        model.addAttribute("user", user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User muser, Model model, HttpServletResponse response1) {

        if(StringUtils.isBlank(muser.getEmail())){
            return new ResponseEntity<>("Email id not found!!",HttpStatus.NO_CONTENT);
        }

        if(StringUtils.isBlank(muser.getPassword())){
            return new ResponseEntity<>("Password not found!!",HttpStatus.NO_CONTENT);
        }

        LoginResponse response = userService.loginUser(muser.getEmail(),muser.getPassword());

        Cookie cookie = new Cookie("JWT_TOKEN", response.getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response1.addCookie(cookie);

        if (response!=null) {
            model.addAttribute("message", "Login successful");
            logger.info(response.getToken());
            logger.info(response.getEmail());
            logger.info(response.getName());
            logger.info(response.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            model.addAttribute("message", "Invalid email or password");
            return new ResponseEntity<>("User not found!!",HttpStatus.NOT_FOUND);
        }
    }

}
