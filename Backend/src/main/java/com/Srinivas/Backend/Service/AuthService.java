package com.Srinivas.Backend.Service;

import com.Srinivas.Backend.Dto.LoginRequest;
import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Dto.UserDto;
import com.Srinivas.Backend.Exception.OurException;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Repository.UserRepo;
import com.Srinivas.Backend.Utils.JWTUtils;
import com.Srinivas.Backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Response Login(LoginRequest loginRequest){
        Response response=new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User Not Found"));
            String token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Success");
        }
        catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while Login User "+e.getMessage());
        }

        return response;
    }
    public  Response register(User user){
        Response response = new Response();
        try{

            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepo.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + "Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDto userDTO = Utils.getUserDtoFromUser(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while saving User"+e.getMessage());

        }
        return response;

    }
}
