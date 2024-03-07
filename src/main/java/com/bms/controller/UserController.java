package com.bms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import com.bms.controller.doc.UserControllerDoc;
import com.bms.requestdto.ChangePasswordRequest;
import com.bms.requestdto.LoginRequest;
import com.bms.requestdto.LoginWIthCode;
import com.bms.requestdto.NumberVerificationRequest;
import com.bms.requestdto.OtpRequest;
import com.bms.requestdto.SignupRequest;
import com.bms.responsedto.LoginResponse;
import com.bms.responsedto.Response;
import com.bms.responsedto.UserResponse;
import com.bms.service.UserService;
import com.bms.util.JWTTokenUtil;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController implements UserControllerDoc {

	private static final long serialVersionUID = 5239827543305326950L;
	
	private final UserService userService;
	
	@PostMapping("/send-otp")
	public Response<String> sendOtp(@Valid @RequestBody OtpRequest request) {
		return userService.sendOtp(request);
	}
	
	@PostMapping("/verify-phone")
	public Response<String> verifyPhoneNumber(@Valid @RequestBody NumberVerificationRequest request){
		return userService.verifyPhoneNumber(request);
	}
	@PostMapping("/signup")
	public Response<UserResponse> signup(@Valid @RequestBody SignupRequest request){
		return userService.signup(request);
	}
	@PostMapping("/login")
	public Response<LoginResponse> login(@Valid @RequestBody LoginRequest request, @RequestHeader("IpAddress") String ipAddress){
		return userService.login(request, ipAddress);
	}
	@PostMapping("/login-with-code")
	public Response<LoginResponse> loginWithCode(@Valid @RequestBody LoginWIthCode request, @RequestHeader("IpAddress") String ipAddress){
		return userService.loginWithCode(request, ipAddress);
	}
	@PostMapping("/forget-password/{Email}")
	public Response<String> forgetPassword(@PathVariable("Email")String email) throws UnsupportedEncodingException, MessagingException {
		return userService.forgetPassword(email);
	}
	@PostMapping("/change-password")
	public Response<String> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION)String token, @RequestBody ChangePasswordRequest request) {
		return userService.changePassword(JWTTokenUtil.getUserIdFromToken(token.substring(7)), request);
	}
	@GetMapping("")
	public Response<UserResponse> getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION)String token){
		return userService.getUserDetails(JWTTokenUtil.getUserIdFromToken(token.substring(7)));
	}
}
