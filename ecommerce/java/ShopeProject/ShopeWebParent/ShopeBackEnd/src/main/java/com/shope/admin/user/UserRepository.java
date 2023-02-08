package com.shope.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shope.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	//public List<User> findAll();
}
