package com.bms.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginWIthCode implements Serializable {
	
	private static final long serialVersionUID = -369332477408297750L;

	@JsonProperty("Email")
	private String email;
	@JsonProperty("Code")
	private String code;
	
}
