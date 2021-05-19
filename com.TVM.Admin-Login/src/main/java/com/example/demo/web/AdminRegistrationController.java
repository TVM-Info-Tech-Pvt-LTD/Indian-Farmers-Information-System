package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.AdminService;
import com.example.demo.web.dto.AdminRegistrationDto;



@Controller

@RequestMapping("/registration")
public class AdminRegistrationController {
	
	private AdminService adminService;

	public AdminRegistrationController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}
	
	@ModelAttribute("admin")
	public AdminRegistrationDto adminRegistrationDto()
	{
		return new AdminRegistrationDto();
	}
	
	@GetMapping
	public String showRegistrationForm()
	{
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("admin") AdminRegistrationDto registrationDto) 
	{
		adminService.save(registrationDto);
		return "success";
	}
	
	
}
	



