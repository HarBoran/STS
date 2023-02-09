package com.shope.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shope.admin.user.UserServcie;

@Controller
public class MainController {
	
	@Autowired
	UserServcie userservice;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

}
