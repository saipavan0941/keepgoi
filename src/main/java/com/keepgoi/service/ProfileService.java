package com.keepgoi.service;

import java.net.http.HttpRequest;

import com.keepgoi.dto.ProfileDto;
import com.keepgoi.model.Profile;

public interface ProfileService {
	
	public Profile getMyprofile(HttpRequest httpRequest);

	public Boolean createProfile(ProfileDto profileDto);
	
}
