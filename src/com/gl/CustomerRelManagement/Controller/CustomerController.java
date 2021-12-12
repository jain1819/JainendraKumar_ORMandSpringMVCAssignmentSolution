package com.gl.CustomerRelManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.CustomerRelManagement.Entity.Customer;
import com.gl.CustomerRelManagement.Service.CustomerService;

public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	//add mapping for "/list"
	@RequestMapping("/list")
	public String listCustomers(Model theModel){
		
		System.out.println("inside listCustomers");
		//Get Customer List from DB
		List<Customer> custListManagement = customerService.findAll();
		//Add to the spring model
		theModel.addAttribute("CustomerList, custListManagement");
		return "list-Customers";
	}

		@RequestMapping("showFormForAdd")
		public String showFormForAdd(Model theModel){
			
			//create model attribute to bind from data
			Customer custAdd = new Customer(); 
			
			theModel.addAttribute("Customer", custAdd);
			return "Customer-form";
		}
		
		@RequestMapping("/showFormForUpdate")
		public String showFormForUdate(@RequestParam("customerId") int Id, Model model){
		
		//get the customer from the service
		Customer customer = customerService.findById(Id); 
		//set Customers as a model attribute to pre-populate the form
		model.addAttribute("Customer", customer);
		//send over to our form
		return "Customer-form";
}
		@PostMapping("/save")
		public String saveCustomer(@RequestParam("id") int id,
				@RequestParam("firstName")String firstName,
				@RequestParam("lastName")String lastName, 
				@RequestParam("email")String email){
			
			Customer customer;
			
			if(id!=0){
				customer = customerService.findById(id);
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				customer.setEmail(email);
			} else{
				customer = new Customer(id, firstName,lastName,email);
			}
			//save the customer
			customerService.save(customer);
			//use a redirect to prevent duplicate submissions
			return "redirect:/customer/list";
				
		}
	@RequestMapping("/delete")	
	public String deleteCustomer (@RequestParam("customerId") int Id){
		//delete the customer
		customerService.deleteById(Id);
		
		return "redirect:/customer/list";
		
	}
}
		
