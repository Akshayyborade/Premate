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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Premate.Service.AdminServices;
import com.Premate.payload.AdminDto;
import com.Premate.util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/admin")
//@Api(tags = "Admin Management")
public class AdminController {

    @Autowired
    AdminServices adminServices;

//    @ApiOperation(value = "Get admin details by ID")
    @GetMapping("/getAdmin/{adminId}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable int adminId) {
        AdminDto admin = adminServices.getAdmin(adminId);
        byte[] profilePicture = adminServices.getProfilePicture(adminId);
        if (profilePicture != null) {
            String base64Image = Base64.getEncoder().encodeToString(profilePicture);
            admin.setBase64Image(base64Image);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

//    @ApiOperation(value = "Get admin picture by ID")
    @GetMapping("/getAdminPicture/{adminId}")
    public ResponseEntity<ApiResponse> getAdminPicture(@PathVariable int adminId) {
        byte[] profilePicture = adminServices.getProfilePicture(adminId);
        ApiResponse apiResponse = new ApiResponse(null, profilePicture);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @ApiOperation(value = "Get all admins")
    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<AdminDto>> getAllAdmin() {
        List<AdminDto> adminAll = adminServices.getAdminAll();
        return new ResponseEntity<>(adminAll, HttpStatus.FOUND);
    }

//    @ApiOperation(value = "Update admin by ID")
    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable int adminId,
                                                @RequestParam("profilePicture") MultipartFile image,
                                                @RequestParam String admin) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String adminJsonString = admin.toString(); // Assuming admin field contains JSON string
        AdminDto updatedAdmin = mapper.readValue(adminJsonString, AdminDto.class);

        // Your logic to handle profile picture upload with image

        // Use the deserialized updatedAdmin object for further processing
        AdminDto updatedAdminResponse = adminServices.updateAdmin(updatedAdmin, adminId, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedAdminResponse);
    }

//    @ApiOperation(value = "Delete admin by ID")
    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int adminId) {
        adminServices.deleteAdmin(adminId);
        return new ResponseEntity<>("admin deleted", HttpStatus.OK);
    }

//    @ApiOperation(value = "Reset password")
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        try {
            boolean passwordResetSuccessful = adminServices.resetPassword(email, currentPassword, newPassword);
            if (passwordResetSuccessful) {
                return ResponseEntity.ok("Password change successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to change password. Check your current password.");
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while resetting the password.");
        }
    }
}
