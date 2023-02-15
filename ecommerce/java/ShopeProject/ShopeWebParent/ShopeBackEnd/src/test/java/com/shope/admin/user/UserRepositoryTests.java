package com.shope.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.shope.common.entity.Role;
import com.shope.common.entity.User;

@DataJpaTest
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
//에디터에서 데이터베이스로 데이터 넣기가 가능해짐
@Rollback(false) 
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	// JUnit test
	@Test
	public void testCreateFirstUserWithOneRole() {
		User UserDavid = new User("david@gmail.com", "David", "Juda", "1234");
		Role roleAdmin = entityManager.find(Role.class, 1);

		// repo.save(UserDavid).addRole(roleAdmin);
		User savedUser = repo.save(UserDavid);
		UserDavid.addRole(roleAdmin);
		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateFirstUserWithManyRole() {
		User UserMaria = new User("maria@email.com", "Maria", "Magdaline", "2023");

		// Role roleAdmin2 = entityManager.find(Role.class, 2);
		Role roleAdmin2 = new Role(2);
		Role roleAdmin3 = new Role(3);
		Role roleAdmin5 = entityManager.find(Role.class, 5);
		UserMaria.addRole(roleAdmin2, roleAdmin5, roleAdmin3);

		repo.save(UserMaria);

//		User savedUser = repo.save(UserMaria);
//		UserMaria.addRole(roleAdmin);
//		assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void findAllUserInfo() {
		Iterable<User> UserAll = repo.findAll();

		for (User user : UserAll) {
			System.out.println(user);
		}

		UserAll.forEach(user -> System.out.println(user));
	}

	@Test
	public void findUserInfo() {
		Optional<User> user = repo.findById(1);
		User user2 = repo.findById(1).get();
		System.out.println(user);
		System.out.println(user2);

	}

	@Test
	public void testUpdateUserDetails() {
		User user = repo.findById(1).get();
		user.setEnabled(true);
		user.setEmail("davidthelastsoneofjJesse@gmail.com");
		repo.save(user);
	}

	@Test
	public void testChangeUserRole() {

		User userMaria = repo.findById(1).get();
		Set<Role> userRoles = userMaria.getRoles();

		Role roleAdmin1 = entityManager.find(Role.class, 1);
		Role roleAdmin2 = entityManager.find(Role.class, 2);
		Role roleAdmin3 = entityManager.find(Role.class, 3);
		Role roleAdmin4 = new Role(4);
		Role roleAdmin5 = new Role(5);

		// roleAdmin1 = Role [id=1, name=Admin, description=manage everything]
		// roleAdmin4 = Role [id=4, name=null, description=null]

		
		  userMaria.getRoles().remove(roleAdmin1);
		  userRoles.remove(roleAdmin2);
		  userRoles.remove(roleAdmin3);
		  userRoles.remove(roleAdmin4);
		  userRoles.remove(roleAdmin5);
//		  userMaria.addRole(roleAdmin1);
//		  userRoles.add(roleAdmin2);
//		  userRoles.add(roleAdmin3);
//		  userRoles.add(roleAdmin4);
//		  userRoles.add(roleAdmin5);
		 

		// repo.save(userMaria);

	}
	
	@Test
	public void getAllUser(){
		//Iterable<User> AllUser = repo.findAll();
		String email = "2222@dddd";
		User user = repo.findByEmail(email);		
		System.out.println(user);
		
		User user2 = repo.getUserByEmail(email);
		assertThat(user2).isNotNull();	
	}
	
	@Test
	public void testCountById(){
		Integer id = 1;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testChangeEnabled() {
		Integer id = 1;
		User user = repo.findById(id).get();
		Boolean b = user.isEnabled();
		//user.setEnabled(!b);
		repo.updateEndabled(id, !b);
		//repo.save(user);
	}
	
	@Test
	public void insertUserThirty() {		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "1234";
		String encodePassword = passwordEncoder.encode(rawPassword);
		
		for (int i=1; i < 31; i ++) {
			User user = new User("Minji@gmail.com"+i, "Danielle", "Haerin", encodePassword);
			Role roleAdmin = entityManager.find(Role.class,(i%5)+1);
			repo.save(user).addRole(roleAdmin);
		}
	}
	
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 3;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
		}

}
