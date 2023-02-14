package com.shope.admin.user;

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
@RequestMapping(value = "/users")
public class UserRestController {
	
	@Autowired
	private UserServcie userservice;
	
	@PostMapping("/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return userservice.isEmailUnique(id, email) ? "OK"  : "Duplicated";
	}
}
