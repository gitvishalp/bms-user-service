package com.bms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.controller.doc.UserControllerDoc;
import com.bms.requestdto.OtpRequest;
import com.bms.responsedto.Response;
import com.bms.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController implements UserControllerDoc {

	private static final long serialVersionUID = 5239827543305326950L;
	
	private final UserService userService;
	
	@PostMapping("send-otp")
	public Response<String> sendOtp(@Valid @RequestBody OtpRequest request) {
		return userService.sendOtp(request);
	}
}
