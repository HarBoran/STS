package com.shope.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shope.common.entity.Category;

@Service
@Transactional
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository repo;

	public List<Category> findAll() {
		return (List<Category>) repo.findAll();
	}

	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Category> hierarchicalCategories() {		
		Iterable<Category> AllCategory= repo.findAll();		
		List<Category> categoriesUsedInForm = new ArrayList<Category>(); 

		for(Category category : AllCategory) {
			if(category.getParent() == null) {
				//System.out.println(category.getName());	
				//categoriesUsedInForm.add(category);
				categoriesUsedInForm.add(new Category(category.getName()));
				
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
					categoriesUsedInForm.add(new Category(name));	
					
					//printChildren(subCategory, 1);
					categoriesUsedInForm.addAll(printChildren(subCategory, 1));
//					for(Category c : (printChildren(subCategory, 1))){
//						H.add(c);
//					}							
				}
			}
		}
		return categoriesUsedInForm;
	}

	private List<Category> printChildren(Category parent, int subLevel) {
		List<Category> H = new ArrayList<Category>();
		
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
			H.add(new Category(name));	
		
//			subCategory.setName(name + subCategory.getName());
//			H.add(subCategory);
//			printChildren(subCategory, newSubLevel);
		}
		return H;
	}


	public void saveCategory(Category newCategory) {
		repo.save(newCategory);
	}
	
	
}
