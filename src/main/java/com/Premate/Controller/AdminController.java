package com.Premate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Premate.Service.AdminServices;
import com.Premate.payload.AdminDto;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	AdminServices adminServices;
	@PostMapping("/register")
   public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto){
		AdminDto dto = adminServices.createAdmin(adminDto);
	   return new ResponseEntity<>(dto,HttpStatus.CREATED);
   }
}
