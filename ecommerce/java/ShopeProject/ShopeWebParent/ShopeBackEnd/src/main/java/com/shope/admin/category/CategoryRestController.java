package com.shope.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shope.common.entity.User;

//a sync방식 
//RESTFUL 웹서비스 방식
@RestController
@RequestMapping(value = "/categories")
public class CategoryRestController {
	
	@Autowired
	private CategoryService categoryservice;
	
	@PostMapping("/check_unique")
	public String checkDuplicateName(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
		boolean n = categoryservice.isNameUnique(id, name);
		boolean a = categoryservice.isAliasUnique(id, alias);
		
		if ((n && a)) {
			return "OK";
		}else if (!n && a) {
			return "NameDuplicated";
		}else if(n && !a) {	
			return "AliasDuplicated";
		}else{
			return "Duplicated";
		}
				
		//return categoryservice.isNameUnique(id, name) ? "OK"  : "Duplicated";
	}

}
