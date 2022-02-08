package com.learning.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name="user")
public class User {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Email
	private String email;
	
	@Size(max=50)
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String address;
}
