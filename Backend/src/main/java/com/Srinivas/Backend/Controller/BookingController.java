package com.Srinivas.Backend.Controller;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Model.Booking;
import com.Srinivas.Backend.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;
    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBookings(@PathVariable Long roomId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Booking bookingRequest) {


        Response response = bookingService.SaveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }



    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> GetAllBookings(){
        Response response=bookingService.GetAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')" )
    public ResponseEntity<Response> CancelBooking(@PathVariable Long  bookingId){
        Response response=bookingService.CancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> GetBookingByConfirmationCode(@PathVariable String confirmationCode){
        Response response=bookingService.FindBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }




}
