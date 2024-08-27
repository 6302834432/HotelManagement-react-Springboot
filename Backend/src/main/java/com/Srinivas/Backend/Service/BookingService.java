package com.Srinivas.Backend.Service;

import com.Srinivas.Backend.Dto.BookingDto;
import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Exception.OurException;
import com.Srinivas.Backend.Model.Booking;
import com.Srinivas.Backend.Model.Room;
import com.Srinivas.Backend.Model.User;
import com.Srinivas.Backend.Repository.BookingRepo;
import com.Srinivas.Backend.Repository.RoomRepo;
import com.Srinivas.Backend.Repository.UserRepo;
import com.Srinivas.Backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    BookingRepo bookingRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private UserRepo userRepo;

    public Response SaveBooking(Long roomId,Long userId,Booking bookingreq){
        Response response=new Response();

        try {
            if (bookingreq.getCheckOutDate().isBefore(bookingreq.getCheckInDate())) {
                throw new IllegalArgumentException("Check in date must come after check out date");
            }
            Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            User user = userRepo.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            List<Booking> existingBookings = room.getBooking();
            if (!roomIsAvailable(bookingreq, existingBookings)) {
                throw new OurException("Room not Available for selected date range");
            }
            bookingreq.setRoom(room);
            bookingreq.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingreq.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepo.save(bookingreq);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        }
        catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Saving Booking "+e.getMessage());

        }
        return response;
    }

    public Response GetAllBookings(){
        Response response = new Response();
            System.out.println("Bookings");
        try{
            List<Booking> bookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDto> bookingDtos = Utils.mapBookingDtoListFromBookingList(bookings);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setBookingList(bookingDtos);
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage("Error while getting Bookings"+e.getMessage());
//            System.out.println(bookingRepo.findAll());
        }
        return response;
    }

    public Response CancelBooking(Long BookingId){
        Response response = new Response();
        try{
            Booking booking = bookingRepo.findById(BookingId).orElseThrow(()->new OurException("Booking not found"));
            bookingRepo.delete(booking);
            response.setStatusCode(200);
            response.setMessage("Success");
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage("Booking not found");
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while Cancel Bookings"+e.getMessage());
        }
        return response;
    }
    public Response FindBookingByConfirmationCode(String confirmationCode){

        Response response = new Response();
        try{
            Booking booking =bookingRepo.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("Booking not saved"));
            BookingDto bookingDto=Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking,true);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setBooking(bookingDto);
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage("Booking not found");
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage("Error while getting Booking"+e.getMessage());
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

}
