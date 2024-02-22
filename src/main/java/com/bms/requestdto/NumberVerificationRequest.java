package com.bms.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NumberVerificationRequest implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -1304907949551924451L;

	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("Otp")
	private String otp;
	
	public NumberVerificationRequest(String phoneNumber, String otp) {
		super();
		this.phoneNumber = phoneNumber;
		this.otp = otp;
	}
	
	
}
