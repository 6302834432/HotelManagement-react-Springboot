package com.Srinivas.Backend.Utils;

import com.Srinivas.Backend.Dto.BookingDto;
import com.Srinivas.Backend.Dto.RoomDto;
import com.Srinivas.Backend.Dto.UserDto;
import com.Srinivas.Backend.Model.Booking;
import com.Srinivas.Backend.Model.Room;
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
    public static RoomDto maprooomDtoFromRoom(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomDescription(room.getRoomDescription());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
        return roomDto;

    }
    public static List<RoomDto> maprooomListEntityToRoomListDTO(List<Room> roomList) {
        return roomList.stream().map(Utils::maprooomDtoFromRoom).collect(Collectors.toList());
    }
    public  static BookingDto mapbookingDtoFromBooking(Booking booking) {
        BookingDto bookingDto=new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setNumOfAdults(booking.getNumOfAdults());
        bookingDto.setNumOfChildren(booking.getNumOfChildren());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        return bookingDto;
    }


}
