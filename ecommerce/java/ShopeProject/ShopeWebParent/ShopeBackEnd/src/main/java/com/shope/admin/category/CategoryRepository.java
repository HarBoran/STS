package com.shope.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shope.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
