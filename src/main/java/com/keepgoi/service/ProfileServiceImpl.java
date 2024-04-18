package com.keepgoi.service;

import java.net.http.HttpRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keepgoi.dto.ProfileDto;
import com.keepgoi.exception.SignUpException;
import com.keepgoi.model.Profile;
import com.keepgoi.model.User;
import com.keepgoi.repo.ProfileRepository;
import com.keepgoi.repo.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Profile getMyprofile(HttpRequest httpRequest) {
		return null;
	}

	@Override
	public Boolean createProfile(ProfileDto profileDto) {
		// TODO Auto-generated method stub
		if(profileDto==null)
			throw new SignUpException("The Input data is empty");
		UserDetails userDetails = userDetailsService.loadUserByUsername(profileDto.getMobileNumber());
		if(userDetails!=null) 
			throw new SignUpException("user already exists");
		Profile profileEntity = new Profile();
		User user = new User();
		BeanUtils.copyProperties(profileDto, profileEntity);
		BeanUtils.copyProperties(profileDto, user);
		profileRepository.save(profileEntity);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return true;
	}

}
