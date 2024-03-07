package com.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByPhoneNumber(String phoneNumber);
	Optional<User> findByEmail(String email);
}
