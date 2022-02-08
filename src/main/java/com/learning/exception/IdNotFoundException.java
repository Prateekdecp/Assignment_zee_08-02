package com.learning.exception;

import lombok.ToString;

@ToString(callSuper = true)
public class IdNotFoundException extends Exception {

	public IdNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
