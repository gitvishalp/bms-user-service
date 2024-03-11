package com.bms.responsedto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponse implements Serializable {
	
	private static final long serialVersionUID = 4587439024188251508L;

	@JsonProperty("AddressId")
	private String id;
	@JsonProperty("AddressLine1")
	private String addressLine1;
	@JsonProperty("AddressLine2")
	private String addressLine2;
	@JsonProperty("City")
	private String city;
	@JsonProperty("State")
	private String state;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("PostalCode")
	private String postalCode;
	@JsonProperty("CreatedAt")
	private Date createdAt;
	
	public AddressResponse(String id, String addressLine1, String addressLine2, String city, String state,
			String country, String postalCode, Date createdAt) {
		super();
		this.id = id;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.createdAt = createdAt;
	}
	
	
}
