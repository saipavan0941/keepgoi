package com.keepgoi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keepgoi.dto.LoginRequest;
import com.keepgoi.dto.ProfileDto;
import com.keepgoi.dto.SMSLogin;
import com.keepgoi.exception.SignInException;
import com.keepgoi.exception.SignUpException;
import com.keepgoi.model.Profile;
import com.keepgoi.model.User;
import com.keepgoi.repo.ProfileRepository;
import com.keepgoi.repo.UserRepository;
import com.keepgoi.util.JwtUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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

	@Value("${ACCOUNT_SID}")
	private String ACCOUNT_SID;
	@Value("${AUTH_TOKEN}")
	private String AUTH_TOKEN;
	@Value("${TWILIO_PHONE_NUMBER}")
	private String TWILIO_PHONE_NUMBER;

	@Autowired
	private JwtUtil jwtUtil;

	private final String TOKEN = "token";
	
    private HashMap<String, Integer> otpMap = new HashMap<>();

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

	@Override
	public Map<String, String> login(LoginRequest loginRequest) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getMobileNumber());
		if (userDetails == null)
			throw new SignInException("invalid credentials");
		if (loginRequest.isLoginWithOTP()) {		
			boolean isCorrectOTP=otpMap.containsKey(loginRequest.getMobileNumber())&&otpMap.containsValue(loginRequest.getOtp());
			if (isCorrectOTP)
				throw new SignInException("invalid OTP");
		}
		else {
		String loginPassword = loginRequest.getPassword();
		User user = userRepository.findByMobileNumber(loginRequest.getMobileNumber());
		boolean isPasswordMatched = passwordEncoder.matches(loginPassword, user.getPassword());
		if (!isPasswordMatched)
			throw new SignInException("invalid password");
		}
		String generatedToken = jwtUtil.generateToken(userDetails);
		return Map.of(TOKEN, generatedToken);
	}

	public String getSMSBody(int otp) {
		return "your OTP for keepgoi is " + otp;
	}
	
	public boolean checkOtp() {
		return false;
	}

	public void sendSMS(String phoneNumber, String message) {
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
			// If the message SID is not null, the SMS was sent successfully
		} catch (Exception e) {
			throw new SignInException("error while sending OTP");
		}
	}

	@Override
	public boolean sendSMS(SMSLogin smsLogin) {
		int number = (int) Math.random();
		int otp = number < 1000 ? number + 1000 : number;
		String getSMSBody=getSMSBody(otp);
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message msg=Message.creator(new PhoneNumber(smsLogin.getMobile()), new PhoneNumber(TWILIO_PHONE_NUMBER), getSMSBody).create();
			if(msg.getSid()!=null) otpMap.put(smsLogin.getMobile(), otp);
			return msg.getSid()!=null;
			// If the message SID is not null, the SMS was sent successfully
		} catch (Exception e) {
			return false;
		}
	}

}
