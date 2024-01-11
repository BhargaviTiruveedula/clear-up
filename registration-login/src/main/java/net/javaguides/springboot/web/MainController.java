package net.javaguides.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}


	@GetMapping("/select")
	public String select() {
		return "select";
	}
	
	@GetMapping("/index")
	public String home() {
		return "index";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/community")
	public String community() {
		return "community";
	}

	@GetMapping("/videos")
	public String videos() {
		return "videos";
	}

	@GetMapping("/forgot")
	public String forgot() {
		return "forgot";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
