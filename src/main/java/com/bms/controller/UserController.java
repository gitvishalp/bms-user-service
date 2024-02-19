package com.bms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.controller.doc.UserControllerDoc;
import com.bms.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController implements UserControllerDoc {

	private static final long serialVersionUID = 5239827543305326950L;
	
	private final UserService userService;
	@GetMapping("add")
	public String check() {
		return userService.addUser();
	}
	
	@GetMapping("otp")
	public String otp() {
		return userService.addOtpType();
	}
}
