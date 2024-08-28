package com.Srinivas.Backend.Service;

import com.Srinivas.Backend.Dto.Response;
import com.Srinivas.Backend.Dto.RoomDto;
import com.Srinivas.Backend.Exception.OurException;
import com.Srinivas.Backend.Model.Room;
import com.Srinivas.Backend.Repository.RoomRepo;
import com.Srinivas.Backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepo roomRepo;
    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }
    @Autowired
    FileServiceImpl fileServiceImpl;
    @Value("${image.storage.path}")
   private String path;

    public Response AddRooom(MultipartFile image, String roomType, BigDecimal roomPrice, String roomDesc) {
        Response response = new Response();
        try {
            String fileName = fileServiceImpl.uploadImage(path,image);
            Room room = new Room();
            room.setRoomPhotoUrl(fileName);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(roomDesc);
            Room data = roomRepo.save(room);
            RoomDto savedroom = Utils.maprooomDtoFromRoom(data);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoom(savedroom);
        } catch (Exception e) {
            response.setStatusCode(500);
            e.printStackTrace();
            response.setMessage("Error while Adding room " + e.getMessage());
        }
        return response;
    }


    public Response DeleteRoom(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            RoomDto roomDto = Utils.maprooomDtoFromRoom(room);
            roomRepo.delete(room);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoom(roomDto);
        } catch (OurException e) {
            response.setStatusCode(500);
            response.setMessage("Room Not Found" + e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while Deleting room " + e.getMessage());
        }
        return response;
    }


    public Response GetAllRooms() {
        Response response = new Response();
        try {
            List<Room> roomList = roomRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDto> rooms = Utils.maprooomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoomList(rooms);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while Getting all rooms" + e.getMessage());
        }
        return response;

    }

    public List<String> GetAllRoomsTypes (){
        return roomRepo.findDistinctRoomTypes();
    }


    public Response GetRoomById(Long id) {
        Response response = new Response();
        try {
            Room room = roomRepo.findById(id).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDto savedroom = Utils.maprooomDtoFromRoom(room);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoom(savedroom);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Room Not Found" + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while Getting room " + e.getMessage());
        }
        return response;
    }


    public Response GetAvailableRoomByDateAndType(LocalDate checkinDate, LocalDate checkoutDate, String roomType) {
        Response response = new Response();
        try {
            List<Room> rooms = roomRepo.findAvailableRoomsByDatesAndTypes(checkinDate, checkoutDate, roomType);
            List<RoomDto> roomDtos = Utils.maprooomListEntityToRoomListDTO(rooms);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoomList(roomDtos);
        } catch (OurException e) {
            response.setStatusCode(500);
            response.setMessage("Error while Getting rooms " + e.getMessage());

        }
        return response;
    }

    public Response GetAllAvailableRooms() {

        Response  response=new Response();
        try{
            List<Room> rooms=roomRepo.getAllAvailableRooms();
            List<RoomDto>roomDtos=Utils.maprooomListEntityToRoomListDTO(rooms);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoomList(roomDtos);
        }
        catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while Getting all available rooms" + e.getMessage());
        }
        return response;
    }


}
