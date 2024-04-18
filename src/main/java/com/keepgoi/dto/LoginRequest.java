package com.keepgoi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

	private String mobileNumber;
	
	private String password;
	
	private boolean isLoginWithOTP;
	
	private int otp;
	
}
