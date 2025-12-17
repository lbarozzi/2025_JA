package it.eforhum.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.eforhum.demo.service.PasswdService;
import it.eforhum.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired 
    PasswdService passwdService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
        var user = userService.findUserByEmail(email);    

        boolean isAuthenticated = user!=null && user.getPassword().equals(
                                    passwdService.hashPassword(password) );
                                    
        Map<String, String> response = new HashMap<>();
        if (isAuthenticated) {
            response.put("status", "success");
            response.put("message", "User authenticated successfully.");
        } else {
            response.put("status", "failure");
            response.put("message", "Invalid username or password.");
        }
        return response;
    }
    
}
