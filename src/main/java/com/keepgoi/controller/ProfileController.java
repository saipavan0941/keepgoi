package com.keepgoi.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoi.dto.ProfileDto;
import com.keepgoi.model.Profile;
import com.keepgoi.service.ProfileService;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

	@Autowired
	ProfileService profileService;
	
	@GetMapping("/myProfile")
	public Profile getMyprofile(HttpRequest httpRequest) {
		return profileService.getMyprofile(httpRequest);
	}
	
	@GetMapping("/register")
	public Boolean register(@RequestBody ProfileDto profileDto) {
		return profileService.createProfile(profileDto);
	}
}
