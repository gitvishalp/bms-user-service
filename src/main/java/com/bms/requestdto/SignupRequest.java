package com.bms.requestdto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class SignupRequest implements Serializable {
	
	private static final long serialVersionUID = -6705271661327798679L;
	
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("DOB")
	private Date dob;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("Password")
	private String password;
	
	public SignupRequest() {
		super();
	}
}
