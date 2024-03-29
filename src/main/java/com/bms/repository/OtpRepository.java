package com.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {

	
	Optional<Otp> findByPhoneNumber(String phoneNumber);
	
}
