package com.keepgoi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {

	@NotEmpty(message = "Name is required")
	private String firstName;

	private String lastName;

	@NotEmpty(message = "mobile number is required")
	private String mobileNumber;

	@Min(value = 18, message = "Age must be at least 18")
	private int age;

	@NotEmpty(message = "Password cannot be empty")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase character, one uppercase character, one special character, and no whitespace")
	private String password;

	@Email(message = "Invalid email format")
	private String email;
}
