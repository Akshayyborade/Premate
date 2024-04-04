package com.Premate.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Premate.Model.Admin;
import com.Premate.Service.AdminServices;
import com.Premate.payload.AdminDto;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	AdminServices adminServices;
	
	@GetMapping("/getAdmin/{adminId}")
	public  ResponseEntity<Admin> getAdmin(@PathVariable int adminId){
		Admin admin = adminServices.getAdmin(adminId);
		return new ResponseEntity<Admin>(admin,HttpStatus.OK);
		
	}
	@GetMapping("/getAllAdmin")
	public ResponseEntity<List<AdminDto>> getAllAdmin(){
	List<AdminDto> adminAll = adminServices.getAdminAll();
	return new ResponseEntity<List<AdminDto>>(adminAll, HttpStatus.FOUND);
	}
	@PutMapping("/updateAdmin/{adminId}")
	public ResponseEntity<AdminDto> updateAdmin(@PathVariable int adminId, @RequestBody AdminDto adminDto  ){
		AdminDto updateAdmin = adminServices.updateAdmin(adminDto, adminId);
		return new ResponseEntity<AdminDto>(updateAdmin,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/deleteAdmin/{adminId}")
	public  ResponseEntity<String> deleteAdmin(@PathVariable int adminId ){
		adminServices.deleteAdmin(adminId);
		return new ResponseEntity<String>("admin deleted", HttpStatus.OK);
		
	}
}
