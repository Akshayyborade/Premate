package com.Premate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Premate.Model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

	
}
