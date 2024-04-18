package com.keepgoi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keepgoi.dto.ProfileDto;
import com.keepgoi.exception.SignInException;
import com.keepgoi.exception.SignUpException;
import com.keepgoi.model.Profile;
import com.keepgoi.model.User;
import com.keepgoi.repo.ProfileRepository;
import com.keepgoi.repo.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Profile getMyprofile(Authentication authentication) {
		String mobileNumber = authentication.getName();
		UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNumber);
		if (userDetails == null)
			throw new SignInException("user not found");
		Profile profile = profileRepository.findById(mobileNumber).orElse(new Profile());
		return profile;
	}

	@Override
	public Boolean createProfile(ProfileDto profileDto) {
		if (profileDto == null)
			throw new SignUpException("The Input data is empty");
		UserDetails userDetails = userDetailsService.loadUserByUsername(profileDto.getMobileNumber());
		if (userDetails != null)
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
