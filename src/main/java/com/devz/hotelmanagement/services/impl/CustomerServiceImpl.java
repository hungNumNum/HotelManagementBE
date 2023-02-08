package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.repositories.CustomerRepository;
import com.devz.hotelmanagement.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public List<Customer> findAll() {
		return customerRepo.findAll();
	}

	@Override
	public Customer findById(int id) {
		 Optional<Customer> optional = customerRepo.findById(id);
	        if (optional.isPresent()) {
	            return optional.get();
	        }
		return null;
	}

	@Override
	public Customer create(Customer customer) {
		customer.setId(null);
        return customerRepo.save(customer);
	}

	@Override
	public Customer upadte(Customer customer) {
		 return customerRepo.save(customer);
	}

}