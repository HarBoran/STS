package com.shope.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shope.common.entity.Role;
import com.shope.common.entity.User;

@Service
public class RoleServcie {
	
	@Autowired
	private RoleRepository roleRepo;
	
	public List<Role> listRoles() {
		return  (List<Role>) roleRepo.findAll();
	}

//	public User FindOne(String email) {
//		return repo.findOne(email);
//	}
}
