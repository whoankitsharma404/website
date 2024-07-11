package com.gl.website.controller;

import com.gl.website.models.User;
import com.gl.website.services.UserService;
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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<User> registerUser(@RequestBody User muser,
                               Model model) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(muser.getName());
        user.setAge(muser.getAge());
        user.setEmail(muser.getEmail());
        user.setPassword(muser.getPassword());
        User savedUser =userService.saveUser(user);
        model.addAttribute("user", savedUser);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User muser, Model model) {
        User user = userService.loginUser(muser.getEmail(),muser.getPassword());
        if (user!=null) {
            model.addAttribute("message", "Login successful");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            model.addAttribute("message", "Invalid email or password");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}
