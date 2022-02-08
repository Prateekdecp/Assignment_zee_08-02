package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Login;
import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
// 						ADDING USER TO THE DATABASE	

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws AlreadyExistsException {
		User result = userService.addUser(user);
		return ResponseEntity.status(201).body(result);
	}

//                     GETTING ALL USERS FROM DATABASE	
	@GetMapping("")
	public ResponseEntity<?> getAllUsers() {
		Optional<List<User>> optional = userService.getAllUsers();
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("string", "no records found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional);
	}

	//                GETTING USER BY ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getByUserId(@PathVariable("id") Integer id) throws IdNotFoundException {
		User user = userService.getUserById(id);
		return ResponseEntity.status(200).body(user);
	}

	//                DELETING USER BY ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Integer id) throws IdNotFoundException {
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		if (result.equals("user deleted successfully"))
			map.put("string", "record deleted successfully");
		return ResponseEntity.status(200).body(map);
	}

	//                 UPDATING USER BY ID
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id")Integer id,@RequestBody User user) throws IdNotFoundException
	{
		User user1=userService.updateUserById(id, user);
		return ResponseEntity.status(200).body(user1);
	}
	
	
	//                 AUTHENTICATION OF USER
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody Login login)
	{
		Map<String, String> map = new HashMap<>();
		boolean result=loginService.authenticateUser(login);
		if(result)
			map.put("message", "success");
		else
			map.put("message", "failure");
		return ResponseEntity.ok(map);
	}

}
