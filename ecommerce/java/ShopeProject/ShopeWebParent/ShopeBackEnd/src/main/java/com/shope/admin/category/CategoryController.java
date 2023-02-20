package com.shope.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shope.common.entity.Category;


@Controller
@RequestMapping(value = "/categories", method = {RequestMethod.GET, RequestMethod.POST})
public class CategoryController {
	
	@Autowired
	CategoryService categoryservice;
	
	@GetMapping({"", "/"})
	public String listAll(Model theModel) {
		List<Category> categoryAll = categoryservice.findAll();
		theModel.addAttribute("categoryAll", categoryAll);
		return "categories";
	}
	
}
