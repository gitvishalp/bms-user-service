package com.bms.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpRequest implements Serializable{

	private static final long serialVersionUID = -6298431443588908936L;
	
	
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	
	
	public OtpRequest(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}
	
}
