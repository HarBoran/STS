package com.shope.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shope.admin.FileUploadUtil;
import com.shope.admin.user.UserNotFoundException;
import com.shope.common.entity.Category;
import com.shope.common.entity.Role;
import com.shope.common.entity.User;

@Controller
@RequestMapping(value = "/categories", method = { RequestMethod.GET, RequestMethod.POST })
public class CategoryController {

	@Autowired
	CategoryService service;

	@GetMapping({ "", "/" })
	public String listAll(Model theModel) {
		List<Category> categoryAll = service.findAll();
		//List<Category> categoryAll = service.hierarchicalCategories();
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
	public String saveCategory(@ModelAttribute("newCategory") Category newCategory, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException { 
		if(!multipartFile.isEmpty()) {
		
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			newCategory.setImage(fileName);
			 
			Category saveCategory = service.saveCategory(newCategory);
			String uploadDir = "../category-images/" + saveCategory.getId();
			
			FileUploadUtil.cleanDir(uploadDir);			
			FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
			
		}else {
			service.saveCategory(newCategory);
		}
		redirectAttributes.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/categories/";
	}
	
	@GetMapping("/edite/{id}")
	public String newCategory(@PathVariable ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
		try{
			Category eidtCategory = service.findById(id);
			theModel.addAttribute("newCategory", eidtCategory);
			
			List<Category> hierarchicalCategories = service.hierarchicalCategories();
			theModel.addAttribute("hierarchicalCategories", hierarchicalCategories);
			theModel.addAttribute("pageTilte", "Edit Category (ID : " + id + ")");
			return "categories/category_form";
			
		}catch(CategoryNotFoundException e){	
			redirectAttributes.addFlashAttribute("message", e.getMessage());			
			return "redirect:/categories/";
		}


		
	}
	

}
