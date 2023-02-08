package com.shope.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shope.admin.user.UserServcie;
import com.shope.admin.user.RoleServcie;
import com.shope.common.entity.User;

@Controller
@RequestMapping(value = "users")
public class UserController {
	
	@Autowired
	UserServcie userservice;
	
	@Autowired
	RoleServcie roleservice; 
	
	@GetMapping("")
	public String listAll(Model theModel) {
		List UserAll = userservice.listAll();
		theModel.addAttribute("UserAll", UserAll);
	
		return "users";
	}
	
	@GetMapping("/new")
	public String newUser(Model theModel) {
		User newUser = new User();
		theModel.addAttribute("newUser", newUser);
		List Role = roleservice.listAll();
		theModel.addAttribute("Role", Role);
		return "joinUser";
	}
	
	@GetMapping("/join")
	public String joinUser(Model theModel) {
		return "redirect:/";
	}

}
