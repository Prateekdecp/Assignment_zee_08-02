package com.learning.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank
	@Size(max=50)
	@Email
	private String email;
	
	@NotBlank
	@Size(min=3,max=20)
	private String name;
	
	@NotBlank
	@Size(min=6,max=40)
	private String password;
	
	@NotBlank
	@Size(min=1)
	private String address;
	
	private Set<String> role;

}
