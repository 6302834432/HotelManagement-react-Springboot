package com.Srinivas.Backend.Repository;

import com.Srinivas.Backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {
}
