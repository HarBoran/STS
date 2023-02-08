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

import com.shope.common.entity.Role;

@DataJpaTest
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) //에디터에서 데이터베이스로 데이터 넣기가 가능해짐
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;
	
	//JUnit test
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		//Role savedRole = repo.save(roleAdmin);
		//assertThat(savedRole.getId()).isGreaterThan(0);
		Role roleAdmin2 = new Role("Salesperson", "manage product price, customers, shipping, orders and sales report");
		Role roleAdmin3 = new Role("Editor", "manage categories, brands, products, articles and menus");
		Role roleAdmin4 = new Role("Shipper", "view products, view orders and update order status");
		Role roleAdmin5 = new Role("Assistant", "manage questions and reviews");

		Iterable<Role> savedRoles = repo.saveAll(List.of(roleAdmin, roleAdmin2, roleAdmin3, roleAdmin4, roleAdmin5));

	}
	
}
