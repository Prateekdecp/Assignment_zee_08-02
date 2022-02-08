package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Food;
import com.learning.dto.Login;
import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.LoginRepository;
import com.learning.repository.UserRepository;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginService loginservice;

	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public User addUser(User user) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		if (userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword()))
			throw new AlreadyExistsException("!! user already exists !!");
		User user1 = userRepository.save(user);
		if (user1 != null) {
			Login login=new Login(user.getEmail(),user.getPassword());
			loginservice.addCredentials(login);
			return user1;
		}
		return null;
	}

	@Override
	public User getUserById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty())
			throw new IdNotFoundException("user with " + id + " not found");
		return optional.get();
	}

	@Override
	public Optional<List<User>> getAllUsers() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findAll());
	}

	@Override
	public String deleteUserById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		try {
			User user = this.getUserById(id);
			if (user == null)
				throw new IdNotFoundException("Sorry user with " + id + " not found");
			else
				return "user deleted successfully";
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException("Sorry user with " + id + " not found");
		}

	}

	@Override
	public User updateUserById(Integer id, User user) throws IdNotFoundException {
		// TODO Auto-generated method stub
		User user1 = getUserById(id);
		if (user1 == null)
			throw new IdNotFoundException("Sorry user with " + id + " not found");
		else {
			userRepository.save(user);
			return user1;
		}
	}

	@Override
	public boolean authenticateUser(String email, String password) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmailAndPassword(email, password);
	}

}
