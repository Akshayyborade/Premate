/*******************************************************************************
 * Premate- School Management System © 2024 by Akshay Borade is licensed under CC BY-NC-SA 4.0 
 *******************************************************************************/
package com.Premate.Controller;

import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Premate.Service.AdminServices;
import com.Premate.payload.AdminDto;
import com.Premate.util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	AdminServices adminServices;

	@GetMapping("/getAdmin/{adminId}")
	public ResponseEntity<AdminDto> getAdmin(@PathVariable int adminId) {
		AdminDto admin = adminServices.getAdmin(adminId);
		byte[] profilePicture = adminServices.getProfilePicture(adminId);
		if(profilePicture!=null) {
		String base64Image = Base64.getEncoder().encodeToString(profilePicture);
		admin.setBase64Image(base64Image);
		}
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);

	}

	@GetMapping("/getAdminPicture/{adminId}")
	public ResponseEntity<ApiResponse> getAdminPicture(@PathVariable int adminId) {
	    byte[] profilePicture = adminServices.getProfilePicture(adminId);
	    ApiResponse apiResponse = new ApiResponse(null, profilePicture);
	    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}


	@GetMapping("/getAllAdmin")
	public ResponseEntity<List<AdminDto>> getAllAdmin() {
		List<AdminDto> adminAll = adminServices.getAdminAll();
		return new ResponseEntity<List<AdminDto>>(adminAll, HttpStatus.FOUND);
	}

	@PutMapping("/updateAdmin/{adminId}")
	public ResponseEntity<AdminDto> updateAdmin(@PathVariable int adminId,
			@RequestParam("profilePicture") MultipartFile image, @RequestParam String admin) throws Exception {
		System.out.println(admin);
		System.out.println("in Update Admin ................................");
		System.out.println(image);
	
//		// Access decoded values directly from admin object:
//		String institutionName = admin.getInstitutionName();
//		String slogan = admin.getSlogan();

		// ... other logic to update admin data with decoded values

		ObjectMapper mapper = new ObjectMapper();
        String adminJsonString = admin.toString(); // Assuming admin field contains JSON string
        AdminDto updatedAdmin = mapper.readValue(adminJsonString, AdminDto.class);

        // ... your logic to handle profile picture upload with image

        // Use the deserialized updatedAdmin object for further processing
        AdminDto updatedAdminResponse = adminServices.updateAdmin(updatedAdmin, adminId, image);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedAdminResponse);
	}

	@DeleteMapping("/deleteAdmin/{adminId}")
	public ResponseEntity<String> deleteAdmin(@PathVariable int adminId) {
		adminServices.deleteAdmin(adminId);
		return new ResponseEntity<String>("admin deleted", HttpStatus.OK);

	}
}
