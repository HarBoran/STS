package com.shope.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shope.admin.user.UserServcie;

@Controller
public class MainController {
	
	@Autowired
	UserServcie userservice;
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

}
