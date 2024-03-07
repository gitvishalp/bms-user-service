package com.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.entity.EmailCode;

public interface EmailCodeRepository extends JpaRepository<EmailCode, String>{
	

}
