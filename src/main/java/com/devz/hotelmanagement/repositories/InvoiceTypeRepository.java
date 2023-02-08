package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.InvoiceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType, Integer> {

}