package com.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.entity.OtpType;

@Repository
public interface OtpTypeRepository extends JpaRepository<OtpType, String> {

}
