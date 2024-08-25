package com.Srinivas.Backend.Repository;

import com.Srinivas.Backend.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {
}
