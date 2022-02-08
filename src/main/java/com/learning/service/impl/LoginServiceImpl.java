package com.learning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Login;
import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginRepository loginRepository;
	@Override
	public String addCredentials(Login login) {
		// TODO Auto-generated method stub
		Login login1=loginRepository.save(login);
		
		return login1!=null?"success":"failure";
	}
	@Override
	public boolean authenticateUser(Login login) {
		// TODO Auto-generated method stub
		Login login2=loginRepository.getById(login.getEmail());
		return login2!=null;
	}

}
