package com.bms.service.impl;


import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bms.constants.Constants;
import com.bms.entity.Otp;
import com.bms.entity.User;
import com.bms.repository.OtpRepository;
import com.bms.repository.UserRepository;
import com.bms.requestdto.OtpRequest;
import com.bms.responsedto.Response;
import com.bms.service.UserService;
import com.bms.util.GenerateOtp;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 6998248696972809679L;
	private final UserRepository userRepository;
	private final OtpRepository otpRepository;
	private final GenerateOtp generateOtp;
	
	@Override
	public Response<String> sendOtp(OtpRequest request) {
		System.out.println(request.getPhoneNumber());
		if(!StringUtils.hasText(request.getPhoneNumber())) {
			return new Response<>(HttpStatus.SC_BAD_REQUEST,"Phone Number is Not valid");
		}
		String regEx = "^([987]{1})(\\d{1})(\\d{8})";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(request.getPhoneNumber());
		if(!matcher.matches()) {
			return new Response<>(HttpStatus.SC_BAD_REQUEST,"Phone Number is Not valid");
		}
		Optional<User> user = userRepository.findByPhoneNumber(request.getPhoneNumber());
		if(!user.isEmpty()) {
			if(user.get().isActive()){
			   return new Response<>(HttpStatus.SC_CONFLICT,"Phone Number Already Exists.Try Login!");
			}
		}
		Otp otp = new Otp();
		Optional<Otp> otpOptional = otpRepository.findByPhoneNumber(request.getPhoneNumber());
		String oneTimePassword = generateOtp.generateOtp().get().toString();
		if(!otpOptional.isEmpty()) {
			final long OTP_VALID_DURATION = 5 * 60 * 1000;
			 long currentTimeInMillis = System.currentTimeMillis();
			 long otpRequestedTimeInMillis = otpOptional.get().getGeneratedTime().getTime();
			 if(otpOptional.get().getOtpCount()>5 && otpRequestedTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis  ) {
					return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"OTP limit exceed!! Try after 5 mins");
				}
			 otp.setOtpCount(otpOptional.get().getOtpCount()+1);
		}else {
			 otp.setOtpCount(otp.getOtpCount()+1);
		}
		otp.setOtp(oneTimePassword);
		otp.setPhoneNumber(request.getPhoneNumber());
		otp.setGeneratedTime(new Date());
		otpRepository.save(otp);
		return new Response<>("OTP send.");
	}
	
	

}
