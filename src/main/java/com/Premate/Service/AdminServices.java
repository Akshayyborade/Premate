package com.Premate.Service;

import java.util.List;

import com.Premate.Model.AppUserRole;
import com.Premate.payload.AdminDto;

public interface AdminServices {
	//Create
	AdminDto createAdmin(AdminDto adminDto);
	//update
	AdminDto updateAdmin(AdminDto adminDto, int id);
	//delete
	AdminDto deleteAdmin(int id);
	//get
	AdminDto getAdmin(int id);
	//getall
	List<AdminDto> getAdminAll();
	//other required 
	AppUserRole getAppUserRole(int id );
}
