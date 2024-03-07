package com.bms.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -8818919885744861571L;
	
	
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("Password")
	private String password;
	
}
