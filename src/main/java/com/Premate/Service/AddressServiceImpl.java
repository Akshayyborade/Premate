package com.Premate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Premate.Model.Address;
import com.Premate.Repository.AddressRepo;
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
 private AddressRepo addressRepo;
	@Override
	public Void saveAddressByStudentID(Address address, int id) {
		try {
//			address.setStud_id(id);
			addressRepo.save(address);
		} catch (Exception e) {
			
			// TODO: handle exception
		}
		return null;
	}

}
