package com.shope.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shope.common.entity.User;

@Service
public class UserServcie {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll() {
		return  (List<User>) userRepo.findAll();
	}
	
	private void encodePassword(User user) {
		String rawPassword = user.getPassword();
		String encodePassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodePassword);
	}
	

	public void save(User user) {
		encodePassword(user);
		userRepo.save(user);
	}

	public User findById(Integer id) {
		return userRepo.findById(id).get();
	}

	public Boolean findByEmail(String emailWritten) {
		User userEmail = userRepo.findByEmail(emailWritten);
		if (userEmail == null) {
			return true;
		}
		return false;
	}
}
