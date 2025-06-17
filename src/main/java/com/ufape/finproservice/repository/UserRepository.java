package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

