package com.Premate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Premate.Model.Admin;
import com.Premate.Repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminServices{

    @Autowired
    private AdminRepo adminRepository;

    public Admin findByEmail(String email) {
        List<Admin> adminList = adminRepository.findByEmail(email);
        System.out.println(adminList);
        return adminList.isEmpty() ? null : adminList.get(0); // or throw an exception if not found
    }
    public Admin regiAdmin(Admin admin) {
		
		Admin ad = adminRepository.save(admin);
		System.out.println(ad);
		return ad;
	}
}
