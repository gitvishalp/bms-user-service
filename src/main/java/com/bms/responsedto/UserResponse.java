package com.bms.responsedto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse implements Serializable {
	
	private static final long serialVersionUID = -8545921019851636776L;
	
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("DOB")
	private LocalDate dob;
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("EmailAddress")
	private String emailAddress;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("IsPhoneNumberVerified")
	private boolean phoneNumberVerified;
	@JsonProperty("IsActive")
	private boolean active;
	@JsonProperty("CreatedAt")
	private Date createdAt;
	
	public UserResponse() {
		super();
	}

}
