package com.shope.admin.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import com.shope.common.entity.Role;
import com.shope.common.entity.User;

@Controller
@RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@Autowired
	RoleService roleservice; 
	
	@GetMapping({"", "/"})
	public String listAll(Model theModel) {
//		List<User> UserAll = userservice.listAll();
//		theModel.addAttribute("UserAll", UserAll);
		return listByPage(1, "id", "asc", null, theModel);
	}
	
	@GetMapping("/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, @Param("sortField")String sortField, @Param("sortDir")String sortDir, @Param("keyword")String keyword,  Model theModel) {
		Date time = new Date();
		System.out.println("+__________+ " + time);

		Page<User> page = userservice.listByPage(pageNum, sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();
		theModel.addAttribute("UserAll", listUsers);
		
		long startCount = (pageNum - 1) * userservice.USERS_PER_PAGE + 1;
		long endCount = startCount + userservice.USERS_PER_PAGE -1;
		if (endCount > page.getTotalElements()) {
			endCount= page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		theModel.addAttribute("currentPage", pageNum);
		theModel.addAttribute("startCount", startCount);
		theModel.addAttribute("endCount", endCount);
		theModel.addAttribute("totalItems", page.getTotalElements());
		
		theModel.addAttribute("sortField", sortField);
		theModel.addAttribute("sortDir", sortDir);		
		theModel.addAttribute("reverseSortDir", reverseSortDir);
		theModel.addAttribute("keyword", keyword);
		
		final long PartPage = 5; //보여줄 컨텐츠의 객수
		long totalPage = page.getTotalPages();
		
		long endPartPage = (long) Math.ceil((double)pageNum /PartPage)*PartPage;
		long startPartPage = endPartPage-PartPage+1;
		if(endPartPage > totalPage) {
			endPartPage = totalPage;
		}
		
		theModel.addAttribute("startPartPage", startPartPage);
		theModel.addAttribute("endPartPage", endPartPage);
	
		theModel.addAttribute("totalPages", page.getTotalPages());

		//데이터 베이스가 비었을 떄를 가정해서
		//theModel.addAttribute("UserAll", new ArrayList<User>());
		//theModel.addAttribute("totalItems", 0);
		
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
	public String saveAndEditeUser(@ModelAttribute("user")User user, Model theModel, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
/*		String emailWritten = user.getEmail();
		Boolean duplicateEmail = userservice.findByEmail(emailWritten);	
		
		if(!duplicateEmail) {
			List<Role> listRoles = roleservice.listAll();
			theModel.addAttribute("listRoles", listRoles);
			theModel.addAttribute("errorMessage", "duplicateEmail");
			return "user-form";
		}
*/	
		
/*		
		userservice.save(user);
		if(user.getId() == 0) {
			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		}else if(user.getId() != 0) {
			redirectAttributes.addFlashAttribute("messageUpdate", "The user has been Update successfully.");
		}
		return "redirect:/users/";
	}
*/
		if(!multipartFile.isEmpty()) {
//			User originalUser = new User();
//			try{
//				originalUser = userservice.findById(user.getId());
//			}catch(UserNotFoundException e){			
//				redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());			
//				return "redirect:/users/";
//			}
//		
//			if(originalUser.getPhotos() != multipartFile.getOriginalFilename()) {
//				String uploadDir = "user-photos/" + originalUser.getId();
//				String fileName = StringUtils.cleanPath(originalUser.getPhotos());
//				FileUploadUtil.delete(uploadDir,fileName);				
//			}
			
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			 user.setPhotos(fileName);
			 User saveUser = userservice.save(user);
			 String uploadDir = "user-photos/" + saveUser.getId();
			 FileUploadUtil.cleanDir(uploadDir);	
			 FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
		} else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
				userservice.save(user);
		}
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users/";
	}
	
	@GetMapping(value = {"/{id}/enabled/{status}"})
	public String editEnabledUserFrom(@PathVariable ("id") Integer id, @PathVariable ("status") boolean enabled, Model theModel, RedirectAttributes redirectAttributes) {

		userservice.updateEndabled(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message ="The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("messageEnabled", message);	
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
		try {
			userservice.deleteById(id);
			redirectAttributes.addFlashAttribute("messageDelete", "The user ID " + id +  " has been deleted successfully.");
		}catch(UserNotFoundException e){
			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());
		}
		return "redirect:/users/";
	}
	
	@GetMapping(value = {"/delete/{id}"})
	public String deleteUserFrom(@PathVariable ("id") Integer id, Model theModel, RedirectAttributes redirectAttributes) {
		try {
			userservice.deleteById(id);
			redirectAttributes.addFlashAttribute("messageDelete", "The user ID " + id +  " has been deleted successfully.");
		}catch(UserNotFoundException e){
			redirectAttributes.addFlashAttribute("messageNotFound", e.getMessage());
		}
		return "redirect:/users/";		
	}
	
}
