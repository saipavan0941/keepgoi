package com.keepgoi.service;

import org.springframework.security.core.Authentication;

import com.keepgoi.dto.ProfileDto;
import com.keepgoi.model.Profile;

public interface ProfileService {
	
	public Profile getMyprofile(Authentication authentication);

	public Boolean createProfile(ProfileDto profileDto);
	
}
