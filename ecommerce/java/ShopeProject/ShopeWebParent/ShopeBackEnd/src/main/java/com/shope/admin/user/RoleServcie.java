package com.shope.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shope.common.entity.Role;

@Service
public class RoleServcie {
	
	@Autowired
	private RoleRepository repo;
	
	public List<Role> listAll() {
		return  (List<Role>) repo.findAll();
	}
}
