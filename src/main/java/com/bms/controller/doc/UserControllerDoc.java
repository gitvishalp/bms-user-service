package com.bms.controller.doc;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.bms.requestdto.ChangePasswordRequest;
import com.bms.requestdto.LoginRequest;
import com.bms.requestdto.LoginWIthCode;
import com.bms.requestdto.NumberVerificationRequest;
import com.bms.requestdto.OtpRequest;
import com.bms.requestdto.SignupRequest;
import com.bms.responsedto.LoginResponse;
import com.bms.responsedto.Response;
import com.bms.responsedto.UserResponse;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.mail.MessagingException;

public interface UserControllerDoc extends Serializable {
	
	
	Response<String> sendOtp(OtpRequest request);
	Response<String> verifyPhoneNumber(NumberVerificationRequest request);
	Response<UserResponse> signup(SignupRequest request);
	Response<LoginResponse> login(LoginRequest request, String ipAddress);
	Response<LoginResponse> loginWithCode(LoginWIthCode request, String ipAddress);
	Response<UserResponse> getUserDetails(@Parameter(hidden = true) String token);
	Response<String> forgetPassword(String email)throws UnsupportedEncodingException,MessagingException;
	Response<String> changePassword(@Parameter(hidden=true)String token,ChangePasswordRequest request);
}
