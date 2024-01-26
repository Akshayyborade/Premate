package com.Premate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Premate.Model.Name;

public interface NameRepo extends JpaRepository<Name, Integer> {

}
