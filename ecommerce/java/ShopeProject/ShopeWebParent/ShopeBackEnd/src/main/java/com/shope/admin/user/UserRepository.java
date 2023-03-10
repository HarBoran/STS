package com.shope.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shope.common.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository/*CrudRepository*/<User, Integer>{

	User findByEmail(String email);

	//JPA 쿼리 구현
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	//@Query("SELECT u FROM User u WHERE u.firstName LIKE %?1%")
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ',u.email, ' ', u.firstname, ' '," + " u.lastname) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);

	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEndabled(Integer id, boolean enabled);
	
}


