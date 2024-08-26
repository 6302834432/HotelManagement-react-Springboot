package com.Srinivas.Backend.Controller;

import com.Srinivas.Backend.Dto.LoginRequest;
import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Response> addUser(@RequestBody User user) {
        Response response =authService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response=authService.Login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
