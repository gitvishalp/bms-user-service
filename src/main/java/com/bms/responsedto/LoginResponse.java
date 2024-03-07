package com.bms.responsedto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse implements Serializable {
	
	private static final long serialVersionUID = -3940880967834694283L;

	
	@JsonProperty("Token")
	private String token;
	
	public LoginResponse() {
		super();
	}
}
