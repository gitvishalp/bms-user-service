package com.bms.service;

import java.io.Serializable;

import com.bms.requestdto.OtpRequest;
import com.bms.responsedto.Response;

public interface UserService extends Serializable {
	
	 Response<String> sendOtp(OtpRequest request);
}
