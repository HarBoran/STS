package com.shope.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shope.common.entity.Category;

@DataJpaTest(showSql = false)
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) // 에디터에서 데이터베이스로 데이터 넣기가 가능해짐
public class CategoryTest {

	@Autowired
	private CategoryRepository repo;

	// @Test
	public void testCreateCategory() {

		Category CategoryComputer = new Category("Computers");
		Category CategoryElectronics = new Category("Electronics");
		repo.save(CategoryComputer);
		repo.save(CategoryElectronics);

		// Category parent = new Category(1); 선생님 방법
		// Category CategoryDesktops = new Category("Desktops", parent);

		Category CategoryDesktops = new Category("Desktops", CategoryComputer);
		Category CategoryLaptops = new Category("Laptops", CategoryComputer);
		Category CategoryComputer_Components = new Category("Computer Components", CategoryComputer);
		Category CategoryCameras = new Category("Cameras", CategoryElectronics);
		Category CategorySmartphones = new Category("Smartphones", CategoryElectronics);

		repo.save(CategoryDesktops);
		repo.save(CategoryLaptops);
		repo.save(CategoryComputer_Components);
		repo.save(CategoryCameras);
		repo.save(CategorySmartphones);

		Category CategoryMemory = new Category("Memory", CategoryComputer_Components);
		Category CategoryGaming_Laptops = new Category("Gaming Laptops", CategoryLaptops);
		Category CategoryiPhone = new Category("iPhone", CategorySmartphones);

		repo.save(CategoryMemory);
		repo.save(CategoryGaming_Laptops);
		repo.save(CategoryiPhone);

		// assertThat(saveCategory.getId()).isGreaterThan(0);

	}

	//@Test
	public void findAllCategory() {
			
		Category category = repo.findById(1).get();
		Set<Category> childerCategory = category.getChildren();
		
		System.out.println(category.getName());	
		for (Category subCategory : childerCategory) {
			System.out.println("--" + subCategory.getName());
		}
	}
	
	
	@Test
	public void testPrintHierarchicalCategories() {		
		Iterable<Category> AllCategory= repo.findAll();
		
		for(Category category : AllCategory) {
			if(category.getParent() == null) {
				System.out.println(category.getName());				
				Set<Category> childern = category.getChildren();
				
				for (Category subCategory : childern) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}

	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			for(int i = 0; i <newSubLevel; i++) {
				System.out.print("--");
			}
			System.out.println(subCategory.getName());		
		
			printChildren(subCategory, newSubLevel);
		}
	}
}
