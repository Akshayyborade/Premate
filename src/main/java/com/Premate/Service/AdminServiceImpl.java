package com.Premate.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return null;
	}

	@Override
	public AdminDto updateAdmin(AdminDto adminDto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdminDto deleteAdmin(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdminDto getAdmin(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdminDto> getAdminAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
