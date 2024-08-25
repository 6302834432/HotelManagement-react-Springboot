package com.Srinivas.Backend.Utils;

import com.Srinivas.Backend.Dto.UserDto;
import com.Srinivas.Backend.Model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static UserDto getUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
    public static List<UserDto> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::getUserDtoFromUser).collect(Collectors.toList());
    }


}
