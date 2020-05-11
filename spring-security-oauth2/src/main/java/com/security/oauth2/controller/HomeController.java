package com.security.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String homePage() {
		return "login";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "admin";
	}

	@GetMapping("/user")
	public String userPage() {
		return "user";
	}
	
	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}
}
