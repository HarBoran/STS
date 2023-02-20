package com.shope.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shope.admin.user.UserRepository;
import com.shope.common.entity.Category;
import com.shope.common.entity.User;

@Service
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryrepository;

	public List<Category> findAll() {
		return (List<Category>) categoryrepository.findAll();
	}
	
}
