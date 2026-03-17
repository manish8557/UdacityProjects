package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final Map<String, Customer> customers = new HashMap<>();

    // static reference (Singleton pattern)
    private static CustomerService instance = new CustomerService();

    private CustomerService() {}
    public static CustomerService getInstance() {
        return instance;
    }


    private boolean isCustomerExists(String email) {
        return customers.containsKey(email);
    }



    public void addCustomer(String email, String firstName, String lastName) {

        if(isCustomerExists(email)){
            System.out.println("Customer already exists.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}