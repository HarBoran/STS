package com.shope.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shope.common.entity.Category;

@Controller
@RequestMapping(value = "/categories", method = { RequestMethod.GET, RequestMethod.POST })
public class CategoryController {

	@Autowired
	CategoryService service;

	@GetMapping({ "", "/" })
	public String listAll(Model theModel) {
		List<Category> categoryAll = service.findAll();
		theModel.addAttribute("categoryAll", categoryAll);
		return "categories/categories";
	}

	@GetMapping("/new")
	public String newCategory(Model theModel) {
		theModel.addAttribute("newCategory", new Category());

		List<Category> hierarchicalCategories = service.hierarchicalCategories();
		theModel.addAttribute("hierarchicalCategories", hierarchicalCategories);

		theModel.addAttribute("pageTilte", "Create New Category");

		return "categories/category_form";
	}

	@PostMapping("/save")
	public String saveCategory(@ModelAttribute("newCategory") Category newCategory, Model theModle, RedirectAttributes redirectAttributes) throws IOException {
		
		 System.out.println(newCategory); 
		 service.saveCategory(newCategory);
		 
		redirectAttributes.addFlashAttribute("message", "The category has been saved successfully.");

		return "redirect:/categories/";
	}

}
