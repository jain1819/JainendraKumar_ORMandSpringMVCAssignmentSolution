package com.gl.CustomerRelManagement.Service;

import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gl.CustomerRelManagement.Entity.Customer;

@Repository
public class CustomerServiceImpl implements CustomerService {
	

	private SessionFactory sessionFactory;
	
	//create session
	private Session session;
	
	@Autowired
	CustomerServiceImpl (SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
		try{
			session = sessionFactory.getCurrentSession();
		} catch(HibernateException e){
			session = sessionFactory.openSession();
		}
	}
	
	@Transactional
	public List<Customer> findAll() {
		
		Transaction trnx = session.beginTransaction();
		List<Customer> customersList = session.createQuery("from Customer").list();
		
		trnx.commit();
		return customersList;
	}
	@Transactional
	public Customer findById(int id) {
		Customer customer = new Customer();
		Transaction trnx = session.beginTransaction();
		customer = session.get(Customer.class, id);
		trnx.commit();
		return customer;
	}
	@Transactional
	public void save(Customer customer) {
		Transaction trnx = session.beginTransaction();
		session.saveOrUpdate(customer);
		trnx.commit();
	}
	@Transactional
	public void deleteById(int id) {
		Transaction trnx = session.beginTransaction();
		Customer customer = session.get(Customer.class, id);
		session.delete(customer);
		trnx.commit();
	}

}
