package com.Srinivas.Backend.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List<BookingDto> bookings = new ArrayList<>();

}
