package com.Srinivas.Backend.Controller;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get-by-id/{userid}")
    public ResponseEntity <Response> GetById(@PathVariable("userid") String userid) {
        Response response=userService.GetById(userid);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @DeleteMapping("/delete/{userid}")
    @PreAuthorize("ADMIN")
    public ResponseEntity<Response> deleteUser(@PathVariable("userid") String userid) {
        Response response=userService.DeleteUser(userid);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Response> GetLoggedInProfileInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response =userService.GetUserInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/get-user-bookings/{userid}")
    public ResponseEntity<Response> GetUserBookings(@PathVariable("userid") String userid) {
        Response response=userService.UserBookingHistory(userid);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}

