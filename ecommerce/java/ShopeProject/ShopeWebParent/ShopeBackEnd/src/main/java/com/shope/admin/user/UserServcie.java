package com.shope.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shope.common.entity.User;

@Service
@Transactional
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
		boolean isUpdateingUser = (user.getId() !=null);
		if(isUpdateingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		userRepo.save(user);
	}

	public User findById(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID "+id);
		}
	}

	public Boolean findByEmail(String emailWritten) {
		User userEmail = userRepo.findByEmail(emailWritten);
		if (userEmail == null) {
			return true;
		}
		return false;
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if(userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(userByEmail != null) return false;
		}else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}


	public void deleteById(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID "+id);
		}
		userRepo.deleteById(id);
	}

	public void updateEndabled(Boolean enabled, Integer id) {
		userRepo.updateEndabled(id, enabled);
	}

}
