package com.bms.controller.doc;

import java.io.Serializable;

import com.bms.requestdto.OtpRequest;
import com.bms.responsedto.Response;

public interface UserControllerDoc extends Serializable {
	
	
	Response<String> sendOtp(OtpRequest request);
	
}
