package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

public interface UserService {

	public User addUser(User user) throws AlreadyExistsException;
	public User getUserById(Integer id) throws IdNotFoundException;
	public Optional<List<User>> getAllUsers();
	public String deleteUserById(Integer id) throws IdNotFoundException;
	public User updateUserById(Integer id,User user) throws IdNotFoundException;
	public boolean authenticateUser(String email,String password);
}
