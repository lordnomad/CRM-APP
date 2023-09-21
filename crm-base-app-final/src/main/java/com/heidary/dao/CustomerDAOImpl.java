package com.heidary.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heidary.entity.Customer;
import com.heidary.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	// inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers(int theSortField) {
	//get the current Hibernate session
	Session session = sessionFactory.getCurrentSession();
	//determine sort field
	String theFieldName=null;
	switch (theSortField) {
	case SortUtils.FIRST_NAME:
		theFieldName = "firstName";
		break;
	case SortUtils.LAST_NAME:
		theFieldName="lastName";
		break;
	case SortUtils.EMAIL:
		theFieldName="email";
		break;
	default:
		//if nothing matches.default to sort:
		theFieldName="lastName";
	}

	//create query
	String queryString = "from Customer order by " + theFieldName;
	Query<Customer> theQuery =  session.createQuery(queryString,Customer.class);

	//execute query and get the result list
	List<Customer> customers = theQuery.getResultList();

	//return the result


	return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		//Get the Current hibernate session
		Session session = sessionFactory.getCurrentSession();
		//Save the Customer
		session.saveOrUpdate(theCustomer);

		
	}

	@Override
	public Customer getCustomer(int theId) {
		//Get the Current hibernate session 
		Session session = sessionFactory.getCurrentSession();
		//get the customer by id
		Customer theCustomer =session.get(Customer.class,theId);
		return theCustomer;

	}

	@Override
	public void deleteCustomer(int theId) {
		//get the current session
		Session session = sessionFactory.getCurrentSession();
		//delete the customer with the PK
		Query theQuery = session.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId",theId);
		theQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		//get the current session
		Session session = sessionFactory.getCurrentSession();
		Query theQuery = null;
		//only search if the theSearchName is not empty
		if(theSearchName!=null && theSearchName.trim().length() > 0) {
			//search for firstName or lastName(case insensitive)
			theQuery=session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName",Customer.class);
			theQuery.setParameter("theName" , "%" + theSearchName.toLowerCase() + "%");
			}
		else {
			//theSearchName is empty.just get all customers
			theQuery=session.createQuery("from Customer",Customer.class);
		}
		//execute query and get result list
		List<Customer> customers=theQuery.getResultList();
		return customers;
	}





}
