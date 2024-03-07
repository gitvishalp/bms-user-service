package com.bms.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {
	
	private static final long serialVersionUID = -7582533196454474315L;
	
	
	@JsonProperty("NewPassword")
	private String newPassword;


	public ChangePasswordRequest(String newPassword) {
		super();
		this.newPassword = newPassword;
	}

}
