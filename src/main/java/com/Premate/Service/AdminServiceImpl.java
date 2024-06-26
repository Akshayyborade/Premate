package com.Premate.Service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Premate.Exception.ResourceNotFoundException;
import com.Premate.Model.Admin;
import com.Premate.Model.AppUserRole;
import com.Premate.Repository.AdminRepo;
import com.Premate.payload.AdminDto;


@Service
public class AdminServiceImpl implements AdminServices{
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public AdminDto createAdmin(AdminDto adminDto) {
		// TODO Auto-generated method stub
		Admin save = adminRepo.save(modelMapper.map(adminDto, Admin.class));
		return modelMapper.map(save, AdminDto.class);
	}

	@Override
	public AdminDto updateAdmin(AdminDto admin, int id,
	MultipartFile image
			) throws IOException{
	    // Retrieve the existing admin by id
	    Admin existingAdmin = adminRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Admin", "Admin id", id));

	    // Set the institutionId from the existing admin
	    admin.setInstitutionId(existingAdmin.getInstitutionId());

	    // Map the properties from the updated admin to the existing admin
	    modelMapper.map(admin, existingAdmin);

	    // Handle image upload
	    if (image != null && !image.isEmpty()) {
	        // Convert MultipartFile to byte array
	        byte[] imageData = image.getBytes();
	        // Set the image data to the existing admin
	        System.out.println(imageData.length);
	        existingAdmin.setProfilePicture(imageData);
	    }

	    // Save the updated admin to the repository
	    Admin updatedAdmin = adminRepo.save(existingAdmin);

	    // Return the updated admin
	    return modelMapper.map(updatedAdmin, AdminDto.class);
	}
	public AdminDto updateAdminDto(AdminDto admin, int id) {
		// TODO Auto-generated method stub
		Admin existingAdmin = adminRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin", "Admin id", id));
		admin.setInstitutionId(existingAdmin.getInstitutionId());
		// Map the properties from adminDto to existingAdmin
	    modelMapper.map(admin, existingAdmin);

	    // Save the updated admin to the repository
	    Admin save = adminRepo.save(existingAdmin);

	    // Map the updated admin back to AdminDto and return
	    return modelMapper.map(save, AdminDto.class);
	}
	@Override
	public AdminDto deleteAdmin(int id) {
		// TODO Auto-generated method stub
		Admin existingAdmin = adminRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin", "Admin id", id));
		adminRepo.delete(existingAdmin);
		return null;
	}

	@Override
	public AdminDto getAdmin(int id) {
		// TODO Auto-generated method stub
		Admin existingAdmin = adminRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin", "Admin id", id));
		return modelMapper.map(existingAdmin, AdminDto.class);
	}

	@Override
	public List<AdminDto> getAdminAll() {
		// TODO Auto-generated method stub
		List<AdminDto> admins = adminRepo.findAll().stream().map(admin-> modelMapper.map(admin, AdminDto.class)).collect(Collectors.toList());
		return admins ;
	}

	@Override
	public AppUserRole getAppUserRole(int id) {
		// TODO Auto-generated method stub
		Admin admin= adminRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin", "Admin id", id));
		return admin.getAppUserRole();
	}

	@Override
	public AdminDto findByEmail(String email) {
		 List<Admin> admins = adminRepo.findByEmail(email);

		    // Handle cases where multiple admins might exist with the same email:
		    if (admins.isEmpty()) {
		        return null; // Or throw an exception if no admin is expected
		    } else if (admins.size() > 1) {
		        // Log a warning or throw an exception if unexpected (should only be one admin)
		        // You might need to handle this scenario based on your application's logic.
		    }

		    // Assuming you expect only one admin:
		    Admin byEmail = admins.get(0);
		    return modelMapper.map(byEmail, AdminDto.class);
	}

	@Override
	public byte[] getProfilePicture(int id) {
		// TODO Auto-generated method stub
		Admin admin =adminRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Admin", "Admin id", id));
		byte[] profilePicture = admin.getProfilePicture();
		return profilePicture;
	}

}
