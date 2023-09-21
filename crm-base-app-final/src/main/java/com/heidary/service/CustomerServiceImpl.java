package com.heidary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heidary.dao.CustomerDAO;
import com.heidary.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDao;

	@Override
	@Transactional
	public List<Customer> getCustomers(int theSortField) {
		return customerDao.getCustomers(theSortField);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {
		customerDao.saveCustomer(theCustomer);

		
	}

	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		
		return customerDao.getCustomer(theId);
	}

	@Override
	@Transactional
	public void deleteCustomer(int theId) {
		customerDao.deleteCustomer(theId);
		
	}

	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName) {
		
		return customerDao.searchCustomers(theSearchName);
	}

}
