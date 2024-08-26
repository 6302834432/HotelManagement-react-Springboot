package com.Srinivas.Backend.Utils;

import com.Srinivas.Backend.Dto.BookingDto;
import com.Srinivas.Backend.Dto.RoomDto;
import com.Srinivas.Backend.Dto.UserDto;
import com.Srinivas.Backend.Model.Booking;
import com.Srinivas.Backend.Model.Room;
import com.Srinivas.Backend.Model.User;

import java.security.SecureRandom;
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
    public static BookingDto mapBookingEntityToBookingDto(Booking booking) {
        BookingDto bookingDTO = new BookingDto();
        // Map simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }
    public static List<BookingDto> mapBookingDtoListFromBookingList(List<Booking> bookings){
        return bookings.stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList());
    }


    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
