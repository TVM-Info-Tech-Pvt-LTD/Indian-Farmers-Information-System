package com.springboot.samplerealtime.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.samplerealtime.project.model.User;
import com.springboot.samplerealtime.project.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String viewHomepage(Model model) {
		return findPaginated(1,"firstName","asc",model);
	}
	@GetMapping("/showNewUserForm")
	public String showNewUserForm(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "new_user" ;
		
	}
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/" ;
		}
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id")long id,Model model) {
		User user=userService.getUserById(id);
		model.addAttribute("user", user);
		return "update_user" ;
	}
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.userService.deleteUserById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo")int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize=5;
		
		Page<User> page=userService.findPaginated(pageNo, pageSize,sortField,sortDir);
		List<User> listUsers=page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")? "desc":"asc");
		
		model.addAttribute("listUsers", listUsers);
		return "index" ;
		
		
		
	}
	
}

