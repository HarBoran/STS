package com.shope.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {
	
	@Autowired
	private UserServcie service;
	
	@PostMapping("/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return service.isEamilUnique(email) ? "OK"  : "Duplicated";
	}

}
