package com.Srinivas.Backend.Controller;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/all")
    @PreAuthorize("ADMIN")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/register")
    public ResponseEntity<Response> addUser(@RequestBody User user) {
        Response response =userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

