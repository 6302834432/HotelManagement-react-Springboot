package com.Srinivas.Backend.Service;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Dto.UserDto;
import com.Srinivas.Backend.Exception.OurException;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Repository.UserRepo;
import com.Srinivas.Backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public Response getAllUsers(){
        Response response = new Response();
       try{
           List<User> users = userRepo.findAll();
           List<UserDto> userDtos = Utils.mapUserListEntityToUserListDTO(users);
           response.setStatusCode(200);
           response.setMessage("Success");
           response.setUserList(userDtos);
       }
       catch(Exception e){
           response.setStatusCode(500);
           response.setMessage("Error while getting All Users"+e.getMessage());
       }
       return response;
    }

    private Response getResponse(String userid) {
        Response response = new Response();
        try{
            User user = userRepo.findById(Long.valueOf(userid)).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDto= Utils.getUserDtoFromUser(user);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setUser(userDto);
            System.out.println(response);
        }
        catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while  User "+e.getMessage());
        }
        return response;
    }
    public Response UserBookingHistory(String userid){
        return getResponse(userid);
    }
    public  Response GetUserInfo(String email){
        Response response = new Response();
        try{
            User user=userRepo.findByEmail(email ).orElseThrow(()->new OurException(("User Not Found")));
            UserDto userDto= Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setUser(userDto);
        }
        catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error While Getting User Info"+e.getMessage());
        }
        return response;
    }
    public Response GetById(String userid){
        return getResponse(userid);
    }


    public Response DeleteUser(String userid){
        Response response = new Response();
        try{
            User user = userRepo.findById(Long.valueOf(userid)).orElseThrow(() -> new OurException("User Not Found"));
            userRepo.delete(user);
            response.setStatusCode(200);
            response.setMessage("Success");

        }
        catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while deleting User "+e.getMessage());
        }
        return response;
    }
//    @Override

}
