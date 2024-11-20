package com.Premate.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.Premate.Model.Admin;
import com.Premate.Model.AppUserRole;
import com.Premate.payload.AdminDto;

public interface AdminServices {
	//Create
	AdminDto createAdmin(AdminDto adminDto);
	//update
	AdminDto updateAdmin(AdminDto admin, int id , MultipartFile image) throws IOException;
	//delete
	AdminDto deleteAdmin(int id);
	//get
	AdminDto getAdmin(int id);
	//getall
	List<AdminDto> getAdminAll();
	//other required 
	AppUserRole getAppUserRole(int id );
	AdminDto findByEmail(String email);
	AdminDto updateAdminDto(AdminDto adminDto, int adminId);
	byte[] getProfilePicture(int id);
	boolean resetPassword(String email, String currentPassword, String newPassword);
}
