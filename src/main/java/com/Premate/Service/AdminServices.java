package com.Premate.Service;

import com.Premate.Model.Admin;

public interface AdminServices {
	public Admin regiAdmin(Admin admin) ;
	 public Admin findByEmail(String email);

}
