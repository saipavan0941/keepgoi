package com.keepgoi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {

	private String firstName;

	private String lastName;

	private double mobileNumber;

	private int age;
	
	private String userName;
}
