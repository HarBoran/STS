package com.shope.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shope.common.entity.Role;
import com.shope.common.entity.User;

@Controller
@RequestMapping(value = "users", method = {RequestMethod.GET, RequestMethod.POST})

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
		theModel.addAttribute("user", newUser);

		List<Role> listRoles = roleservice.listRoles();
		theModel.addAttribute("listRoles", listRoles);
		
		theModel.addAttribute("pageTilte", "Create New User");
		return "user-form";
	}
	
	@PostMapping("/save")
	public String saveAndEditeUser(@ModelAttribute("user")User user, Model theModel, RedirectAttributes redirectAttributes) {
/*		String emailWritten = user.getEmail();
		Boolean duplicateEmail = userservice.findByEmail(emailWritten);	
		
		if(!duplicateEmail) {
			List<Role> listRoles = roleservice.listAll();
			theModel.addAttribute("listRoles", listRoles);
			theModel.addAttribute("errorMessage", "duplicateEmail");
			return "user-form";
		}
*/	
		userservice.save(user);
		if(user.getId() == 0) {
			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		}else if(user.getId() != 0) {
			redirectAttributes.addFlashAttribute("messageUpdate", "The user has been Update successfully.");
		}
		return "redirect:/users/";
	}

	@RequestMapping(value = {"/edite"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String editUser(@RequestParam ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
		try{
			User editUser = userservice.findById(id);
			theModel.addAttribute("user", editUser);
		}catch(UserNotFoundException e){
		
			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());			
			return "redirect:/users/";
		}
		List<Role> listRoles = roleservice.listRoles();
		theModel.addAttribute("listRoles", listRoles);
		
		theModel.addAttribute("pageTilte", "Edit User (ID : " + id + ")");
		
		return "user-form";
		
	}
	
	@GetMapping(value = {"/edite/{id}"})
	public String editUserFrom(@PathVariable ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
		
		try{
			User editUser = userservice.findById(id);
			theModel.addAttribute("user", editUser);
		}catch(UserNotFoundException e){
		
			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());			
			return "redirect:/users/";
		}
		List<Role> listRoles = roleservice.listRoles();
		theModel.addAttribute("listRoles", listRoles);
		
		theModel.addAttribute("pageTilte", "Edit User (ID : " + id + ")");
		
		return "user-form";
		
	}
	
	@RequestMapping(value = {"/delete"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteUser(@RequestParam ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
//		try {
//			userservice.deleteById(id);
			redirectAttributes.addFlashAttribute("messageDelete", "The user ID " + id +  " has been deleted successfully.");
//		}catch(UserNotFoundException e){
//			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());
//		}
		return "redirect:/users/";
	}
	
	@GetMapping(value = {"/delete/{id}"})
	public String deleteUserUserFrom(@PathVariable ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
//		try {
//			userservice.deleteById(id);
			redirectAttributes.addFlashAttribute("messageDelete", "The user ID " + id +  " has been deleted successfully.");
//		}catch(UserNotFoundException e){
//			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());
//		}
		return "redirect:/users/";		
	}
	
}
