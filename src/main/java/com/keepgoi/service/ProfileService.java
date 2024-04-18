package com.keepgoi.service;

import java.util.Map;

import org.springframework.security.core.Authentication;

import com.keepgoi.dto.LoginRequest;
import com.keepgoi.dto.ProfileDto;
import com.keepgoi.dto.SMSLogin;
import com.keepgoi.model.Profile;

public interface ProfileService {
	
	public Profile getMyprofile(Authentication authentication);

	public Boolean createProfile(ProfileDto profileDto);

	public Map<String, String> login(LoginRequest loginRequest);

	public boolean sendSMS(SMSLogin smsLogin);
	
}
