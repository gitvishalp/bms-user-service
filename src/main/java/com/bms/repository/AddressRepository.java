package com.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String> {

	Optional<Address> findByUserId(String userId);
}
