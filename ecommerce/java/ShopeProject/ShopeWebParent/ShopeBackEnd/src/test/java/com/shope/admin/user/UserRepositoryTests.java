	package com.shope.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shope.common.entity.user;

@DataJpaTest
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) //에디터에서 데이터베이스로 데이터 넣기가 가능해짐
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	//JUnit test
	@Test
	public void testCreateFirstUser() {
	
		//user UserAdmin = new user("A@a.gmail", true,"minsoo", "ha", "1234", "photo");
		user UserAdmin = new user("A12Aa@a.gmail","minsoo", "ha", "1234");
		user savedUser = repo.save(UserAdmin);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
}
