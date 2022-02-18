package com.learning.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank
	@Size(max=50)
	String email;
	
	@NotBlank
	String password;
}
