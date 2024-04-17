package com.keepgoi.service;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Service;

import com.keepgoi.model.Profile;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Override
	public Profile getMyprofile(HttpRequest httpRequest) {
		return null;
	}

}
