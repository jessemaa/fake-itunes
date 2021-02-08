package com.example.fakeitunes.fakeitunes.controllers;

        import com.example.fakeitunes.fakeitunes.dataaccess.CustomerRepository;
        import com.example.fakeitunes.fakeitunes.models.Country;
        import com.example.fakeitunes.fakeitunes.models.Customer;
        import com.example.fakeitunes.fakeitunes.models.HighestSpender;
        import org.springframework.web.bind.annotation.*;
        import java.util.ArrayList;
        import java.util.HashMap;

@RestController
public class CustomerController {

    // Configure some endpoints
    private CustomerRepository customerRepository = new CustomerRepository();

    // This one returns all the customers in the database
    @GetMapping("/api/customers")
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    // This adds a new customer.
    @PostMapping("/api/customers") // KEEP
    public Boolean addNewCustomer(@RequestBody Customer customer) {
        return customerRepository.addCustomer(customer);
    }

    //This updates an existing customer.
    @PutMapping("/api/customers/{id}") // KEEP
    public Boolean updateExistingCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    // This lists customers by countries in descending order
    @GetMapping("/api/customer/countries")
    public ArrayList<Country> getCustomersByCountries() {
        return customerRepository.getCustomersByCountries();
    }

    // This lists the highest spenders in descending order
    @GetMapping("/api/customer/highestspenders")
    public ArrayList<HighestSpender> getHighestSpenders() {
        return customerRepository.getHighestSpenders();
    }

    // This returns the most popular genre with given customer id
    @GetMapping("/api/customer/mostpopulargenre/{id}")
    public HashMap<String, String> getPopularGenre(@PathVariable String id) {
        return customerRepository.getPopularGenre(id);
    }
}
