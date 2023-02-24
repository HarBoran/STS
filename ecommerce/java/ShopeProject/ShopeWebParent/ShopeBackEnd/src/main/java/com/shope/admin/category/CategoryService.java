package com.shope.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shope.common.entity.Category;
import com.shope.common.entity.User;

@Service
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;

	public List<Category> findAll() {
//		return (List<Category>) repo.findAll();
		List<Category> rootCategories = repo.findRootCategories();
		return listHierarchicalCategories(rootCategories);
	}
	
	public List<Category> listHierarchicalCategories(List<Category> rootCategories) {			
		List<Category> hierarchicalCategories = new ArrayList<Category>(); 

		for(Category category : rootCategories) {
				//System.out.println(category.getName());	
				//categoriesUsedInForm.add(category);
				//categoriesUsedInForm.add(new Category(category.getName()));
				hierarchicalCategories.add(Category.copyFull(category));
				Set<Category> childern = category.getChildren();
				
				for (Category subCategory : childern) {
					//System.out.println("--" + subCategory.getName());
//					Category c = new Category();
//					c = subCategory;
//					c.setName("--" + subCategory.getName());
//					H.add(c);	
			//		subCategory.setName("--" + subCategory.getName());
			//		categoriesUsedInForm.add(subCategory);	
					String name = "--" + subCategory.getName();
					//categoriesUsedInForm.add(new Category(name));
					hierarchicalCategories.add(Category.copyFull(subCategory, name));
						//printChildren(subCategory, 1);
//					categoriesUsedInForm.addAll(printChildren(subCategory, 1));
					listSubHierarchicalCatrgories(hierarchicalCategories, subCategory, 1);
				
			}
		}
		return hierarchicalCategories;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Category> hierarchicalCategories() {		
		Iterable<Category> AllCategory= repo.findAll();		
		List<Category> categoriesUsedInForm = new ArrayList<Category>(); 

		for(Category category : AllCategory) {
			if(category.getParent() == null) {
				//System.out.println(category.getName());	
				//categoriesUsedInForm.add(category);
				//categoriesUsedInForm.add(new Category(category.getName()));
				categoriesUsedInForm.add(Category.copyFull(category));
				Set<Category> childern = category.getChildren();
				
				for (Category subCategory : childern) {
					//System.out.println("--" + subCategory.getName());
//					Category c = new Category();
//					c = subCategory;
//					c.setName("--" + subCategory.getName());
//					H.add(c);	
			//		subCategory.setName("--" + subCategory.getName());
			//		categoriesUsedInForm.add(subCategory);	
					String name = "--" + subCategory.getName();
					Category parent = subCategory.getParent();
					//categoriesUsedInForm.add(new Category(name));
					categoriesUsedInForm.add(Category.copyFull(subCategory, name));
						//printChildren(subCategory, 1);
//					categoriesUsedInForm.addAll(printChildren(subCategory, 1));
					listSubHierarchicalCatrgories(categoriesUsedInForm, subCategory, 1);
						
				}
			}
		}
		return categoriesUsedInForm;
	}
	
	private List<Category> listSubHierarchicalCatrgories(List<Category>categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			String name = "";
			for(int i = 0; i <newSubLevel; i++) {
				//System.out.print("--");	
				name += "--";							
			}
			//System.out.println(subCategory.getName());
			name += subCategory.getName();
			//H.add(new Category(name));	
//			H.add(subCategory);
//			subCategory.setName(name + subCategory.getName());
			categoriesUsedInForm.add(Category.copyFull(subCategory, name));
			listSubHierarchicalCatrgories(categoriesUsedInForm, subCategory, newSubLevel);
		}
		return categoriesUsedInForm;
	}

	

	public Category saveCategory(Category newCategory) {
		return repo.save(newCategory);
	}


	public Category findById(Integer id) throws CategoryNotFoundException{
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with Id" + id);
		}
	}

	public boolean isNameUnique(Integer id, String name) {
		Category categoryByName = repo.getCategoryByName(name);
		if(categoryByName == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(categoryByName != null) return false;
		}else {
			if(categoryByName.getId() != id) {
				return false;
			}
		}
		return true;
	}


	public boolean isAliasUnique(Integer id, String alias) {
		Category categoryByAlias = repo.getCategoryByAlias(alias);
		if(categoryByAlias == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(categoryByAlias != null) return false;
		}else {
			if(categoryByAlias.getId() != id) {
				return false;
			}
		}
		return true;
	}
	
	
	
}
