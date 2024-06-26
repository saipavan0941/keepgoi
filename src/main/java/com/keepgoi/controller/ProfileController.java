package com.keepgoi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoi.dto.LoginRequest;
import com.keepgoi.dto.ProfileDto;
import com.keepgoi.dto.SMSLogin;
import com.keepgoi.model.Profile;
import com.keepgoi.service.ProfileService;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

	@Autowired
	ProfileService profileService;
	
	@GetMapping("/myProfile")
	public Profile getMyprofile(Authentication authentication) {
		return profileService.getMyprofile(authentication);
	}
	
	@GetMapping("/register")
	public Boolean register(@RequestBody ProfileDto profileDto) {
		return profileService.createProfile(profileDto);
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody LoginRequest loginRequest){
		return profileService.login(loginRequest);
	}
	
	@PostMapping("/sendSMS")
	public boolean sendSMS(@RequestBody SMSLogin smsLogin) {
		return profileService.sendSMS(smsLogin);
	}
}
