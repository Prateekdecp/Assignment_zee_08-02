package com.learning.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.EROLE;
import com.learning.dto.Role;
import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.payload.request.LoginRequest;
import com.learning.payload.request.SignupRequest;
import com.learning.payload.response.JwtResponse;
import com.learning.payload.response.MessageResponse;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.security.services.UserDetailsImpl;
import com.learning.service.UserService;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {

	@Autowired
	UserService userService;


	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetailsImpl.getAuthorities().stream().map(i -> i.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), 
				userDetailsImpl.getUsername(), userDetailsImpl.getEmail(), roles));
	}


// 						ADDING USER TO THE DATABASE	

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signupRequest) throws AlreadyExistsException {
		if(userRepository.existsByEmailAndPassword(signupRequest.getEmail(), signupRequest.getPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already exists"));
		}
		User user = new User(signupRequest.getEmail(), signupRequest.getName(),
				passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getAddress());
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<Role>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(e -> {
				switch (e) {
				case "admin":
					Role roleAdmin = roleRepository.findByRoleName(EROLE.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role not found"));
					roles.add(roleAdmin);
					break;
				default:
					Role roleUser = roleRepository.findByRoleName(EROLE.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role not found"));
					roles.add(roleUser);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.status(201).body(new MessageResponse("user created successfully"));
	}

//                     GETTING ALL USERS FROM DATABASE	
	@GetMapping("")
	public ResponseEntity<?> getAllUsers() {
		Optional<List<User>> optional = userService.getAllUsers();
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("no such user found"));
		}
		return ResponseEntity.ok(optional);
	}

	// GETTING USER BY ID
	@GetMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getByUserId(@PathVariable("id") Integer id) throws IdNotFoundException {
		User user = userService.getUserById(id);
		return ResponseEntity.status(200).body(user);
	}

	// DELETING USER BY ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Integer id) throws IdNotFoundException {
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		if (result.equals("user deleted successfully"))
			map.put("string", "record deleted successfully");
		return ResponseEntity.status(200).body(map);
	}

	// UPDATING USER BY ID
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id") Integer id, @RequestBody User user)
			throws IdNotFoundException {
		User user1 = userService.updateUserById(id, user);
		return ResponseEntity.status(200).body(user1);
	}

	

}
