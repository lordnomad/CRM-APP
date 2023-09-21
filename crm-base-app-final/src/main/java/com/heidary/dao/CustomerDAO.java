package com.heidary.dao;

import java.util.List;

import com.heidary.entity.Customer;

public interface CustomerDAO {
	public List<Customer> getCustomers(int theSortField);

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theId);

	public List<Customer> searchCustomers(String theSearchName);
}
