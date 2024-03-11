package com.bms.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.bms.requestdto.AddressRequest;
import com.bms.requestdto.ChangePasswordRequest;
import com.bms.requestdto.LoginRequest;
import com.bms.requestdto.LoginWIthCode;
import com.bms.requestdto.NumberVerificationRequest;
import com.bms.requestdto.OtpRequest;
import com.bms.requestdto.SignupRequest;
import com.bms.responsedto.AddressResponse;
import com.bms.responsedto.LoginResponse;
import com.bms.responsedto.Response;
import com.bms.responsedto.UserResponse;

import jakarta.mail.MessagingException;

public interface UserService extends Serializable {
	
	 Response<String> sendOtp(OtpRequest request);
	 Response<String> verifyPhoneNumber(NumberVerificationRequest request);
	 Response<UserResponse> signup(SignupRequest request);
	 Response<LoginResponse> login(LoginRequest request, String ipAddress);
	 Response<LoginResponse> loginWithCode(LoginWIthCode request, String ipAddress);
	 Response<UserResponse> getUserDetails(String userId);
	 Response<String> forgetPassword(String email) throws UnsupportedEncodingException,MessagingException;
	 Response<String> changePassword(String userId, ChangePasswordRequest request);
	 Response<String> resetPasswordLink(String email,String code);
	 Response<String> resetPassword(String userId, ChangePasswordRequest request);
	 Response<AddressResponse> updateAddress(String userId, AddressRequest request);
}
