package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Premate.Model.Admin;
import com.Premate.Model.AppUserRole;
import com.Premate.Service.AdminServices;

@Controller
public class HomeController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AdminServices adminServices;
	

	@GetMapping("/admin_register")
	public String register() {
		return "registration/Admin-registration";
	}

	@GetMapping("/adminlogin")
	public String adminlogin() {

		return "login/adminlogin";
	}

	@PostMapping("/adminReg")
	public String adminReg(@ModelAttribute Admin admin, Model model, BCryptPasswordEncoder encoder) {
		System.out.println("reg process working");
		String passString= encoder.encode(admin.getPassword());
		admin.setPassword(passString);
		admin.setEnabled(true);
		admin.setAppUserRole(AppUserRole.ADMIN);
		Admin adminExist = adminServices.findByEmail(admin.getEmail());
		if (adminExist==null) {
			adminServices.regiAdmin(admin);	
			model.addAttribute( "msg", "user Registered Sucessfully");
		}else {
			model.addAttribute( "msg", "user Already Exist");
			
		}
		
		return "redirect:/admin_register";
	}
//
//	@GetMapping("/login")
//	@ResponseBody
//	public String index() {
//		return "index login";
//	}

//	@PostMapping("/adminLogin")
//	public String adminLogin() {
//		
//		return "working";
//	}
	@PostMapping("/adminLogin")
	public String processLogin(@RequestParam String username, @RequestParam String password) {
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println("login");
			
			return "redirect:/home"; // Redirect to intended page
		} catch (AuthenticationException e) {
			e.printStackTrace();
			System.out.println("error bhava ");
			return "redirect:/adminlogin?error";
			
		}
	}
	@RequestMapping("/home")
	@ResponseBody
	public String admindash() {
		return "hii admin how are you";
		
	}

}
