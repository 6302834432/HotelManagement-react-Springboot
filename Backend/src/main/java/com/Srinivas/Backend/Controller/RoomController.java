package com.Srinivas.Backend.Controller;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> AddRoom(@RequestParam(value = "image", required = false) MultipartFile imageUrl, @RequestParam(value = "roomType", required = false) String roomType, @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice, @RequestParam(value = "roomDescription", required = false) String roomDescription) {
        System.out.println("Data room:" + roomType + roomPrice + roomDescription);
        Response response = roomService.AddRooom(imageUrl, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId, @RequestParam(value = "photo", required = false) MultipartFile photo, @RequestParam(value = "roomType", required = false) String roomType, @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice, @RequestParam(value = "roomDescription", required = false) String roomDescription

    ) {
        Response response = roomService.updateRoom(roomId, roomDescription, roomType, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Response> GetAllRooms() {
        Response response = roomService.GetAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/types")
    public List<String> GetAllRoomTypes() {
        return roomService.GetAllRoomsTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> GetRoomById(@PathVariable("roomId") Long roomId) {
        Response response = roomService.GetRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> GetAllAvailableRooms() {
        Response response = roomService.GetAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> GetAvailableRoomsByDateAndType(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate, @RequestParam(required = false) String roomType) {
        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomService.GetAvailableRoomByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Response> DeleteRoom(@PathVariable("roomId") Long roomId) {
        Response response = roomService.DeleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
