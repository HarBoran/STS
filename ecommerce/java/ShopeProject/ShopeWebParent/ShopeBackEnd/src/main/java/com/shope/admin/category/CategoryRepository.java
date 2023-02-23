package com.shope.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shope.common.entity.Category;
import com.shope.common.entity.User;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories();
	
	//JPA 쿼리 구현
	@Query("SELECT c FROM Category c WHERE c.name = :name")
	public Category getCategoryByName(@Param("name")String name);

	@Query("SELECT c FROM Category c WHERE c.alias = :alias")
	public Category getCategoryByAlias(@Param("alias")String alias);

	public Category findByName(String name);
	
	public Category findByAlias(String Alias);
}
