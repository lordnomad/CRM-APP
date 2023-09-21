package com.heidary.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heidary.entity.Customer;
import com.heidary.service.CustomerService;
import com.heidary.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerContoller {
	
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/list")
	public String showHome(Model theModel,@RequestParam(required=false) String sort) {
		//get the customers form DAO
		List<Customer> theCustomers = null;
		//check for sort field
		if(sort!=null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
			}
		else {
			//no sort field provided default to sorting by last
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
			
		}
		//add the customers the the model
		theModel.addAttribute("customers",theCustomers);

		
		return "list-customers";
		}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
		}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer theCustomer,BindingResult theBindingResult) {
		if(theBindingResult.hasErrors()) {
			return "customer-form";
		}else {
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";}
		}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,Model theModel) {
		
		//get the customer from DB
		Customer theCustomer = customerService.getCustomer(theId);
		//set the customer as a model attribute
		theModel.addAttribute("customer", theCustomer);
		
		//send over to customer form
		return "customer-form";
		}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
		}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName,Model theModel) {
		// search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
                
        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);
        return "list-customers";        

		
	}



}
