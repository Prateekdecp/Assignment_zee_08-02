package com.learning.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;


@ControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?> alreadyRecordExistsException(AlreadyExistsException e)
	{
		Map<String,String> map=new HashMap<>();
		map.put("string", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundException(IdNotFoundException e)
	{
		Map<String,String> map=new HashMap<>();
		map.put("string", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
	
}
