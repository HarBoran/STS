package com.shope.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shope.admin.user.UserServcie;
import com.shope.admin.user.RoleServcie;
import com.shope.common.entity.Role;
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
		List<User> UserAll = userservice.listAll();
		theModel.addAttribute("UserAll", UserAll);
	
		return "users";
	}
	
	@GetMapping("/new")
	public String newUser(Model theModel) {
		User newUser = new User();
		newUser.setEnabled(true);
		theModel.addAttribute("newUser", newUser);
		List<User> allUser = userservice.listAll();
		
		List<Role> listRoles = roleservice.listAll();
		theModel.addAttribute("listRoles", listRoles);
		return "user-form";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("newUser")User newUser, Model theModel) {
		String emailWritten = newUser.getEmail();
		Boolean duplicateEmail = userservice.findByEmail(emailWritten);	
		
		if(!duplicateEmail) {
			List<Role> listRoles = roleservice.listAll();
			theModel.addAttribute("listRoles", listRoles);
			theModel.addAttribute("duplicateEmail", "duplicateEmail");
			//theModel.addFlashAttribute("duplicateEmail", duplicateEmail);
			System.out.println("---------------------------------------------------------");
			return "user-form";
		}
	
		userservice.save(newUser);
		return "redirect:/users/";
	}
	
	@PostMapping("/edit")
	public String editUser(@RequestParam ("id") Integer id, Model theModel) {
		User newUser = userservice.findById(id);
		theModel.addAttribute("newUser", newUser);
		
		List<Role> listRoles = roleservice.listAll();
		theModel.addAttribute("listRoles", listRoles);
		return "user-form";
	}
	


}
