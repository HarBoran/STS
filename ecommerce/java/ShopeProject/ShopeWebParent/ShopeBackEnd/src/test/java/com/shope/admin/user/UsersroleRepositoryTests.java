//package com.shope.admin.user;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import com.shope.common.entity.user;
//import com.shope.common.entity.users_role;
//
//@DataJpaTest
////데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false) //에디터에서 데이터베이스로 데이터 넣기가 가능해짐
//public class UsersroleRepositoryTests {
//	
//	@Autowired
//	private UsersroleRepository repo;
//	
//	//JUnit test
//	@Test
//	public void testCreateFirstUsersrole() {
//		
//		users_role usersroleAdmin = new users_role();
//		users_role savedUserrole = repo.save(usersroleAdmin);
//		
//	}
//	
//}
