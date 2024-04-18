package com.keepgoi.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSLogin {
	
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid mobile number")
	private String mobile;
}
