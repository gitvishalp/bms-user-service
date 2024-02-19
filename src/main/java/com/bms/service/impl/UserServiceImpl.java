package com.bms.service.impl;

import org.springframework.stereotype.Service;

import com.bms.constants.Constants;
import com.bms.entity.OtpType;
import com.bms.entity.User;
import com.bms.repository.OtpTypeRepository;
import com.bms.repository.UserRepository;
import com.bms.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 6998248696972809679L;
	private final UserRepository userRepository;
	private final OtpTypeRepository otpTypeRepository;

	@Override
	public String addUser() {
		try {
			User user = new User();
			user.setFirstName("vishal");
			user.setLastName("prajapati");
			user.setPhoneNumber("8368152619");
			user.setActive(true);
			user.setEmail("vishal@cqs.in");
			userRepository.save(user);
			return "success";
		}catch(Exception ex) {
			return ex.toString();
		}
	}

	@Override
	public String addOtpType() {
		OtpType otpType =new OtpType();
		otpType.setCode(Constants.OTP_TYPE_OTHER);
		otpType.setActive(true);
		otpType.setDescription("other otp");
		otpType.setName("Other Otp");
		otpTypeRepository.save(otpType);
		return "success";
	}

}
