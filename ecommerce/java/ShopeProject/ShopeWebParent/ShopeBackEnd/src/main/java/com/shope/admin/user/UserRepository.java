package com.shope.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shope.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	User findByEmail(String email);

	//JPA 쿼리 구현
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
}
