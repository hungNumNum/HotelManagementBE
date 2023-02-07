package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRoleRepository extends JpaRepository<UserRole, Integer> {

}
