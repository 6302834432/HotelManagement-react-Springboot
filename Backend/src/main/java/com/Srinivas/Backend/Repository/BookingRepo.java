package com.Srinivas.Backend.Repository;

import com.Srinivas.Backend.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
