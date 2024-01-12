package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.Premate.Model.Admin;
import com.Premate.Service.AdminServices;

@Controller

public class AdminController {
//	@Autowired
//	private AdminServices registration;
//	private Admin ad;
////	private AuthenticationManager authenticationManager = null;
////
////	public Registration(AuthenticationManager authenticationManager) {
////		this.authenticationManager = authenticationManager;
////	}
//
//	@PostMapping("/adminReg")
//	public String adminReg(@ModelAttribute Admin admin) {
//		ad= registration.regiAdmin(admin);
//		System.out.println(ad.toString());
//		return "redirect:/admin_register"; 
//	}
}
