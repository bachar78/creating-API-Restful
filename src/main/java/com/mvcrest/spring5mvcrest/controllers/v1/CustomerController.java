package com.mvcrest.spring5mvcrest.controllers.v1;

import com.mvcrest.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mvcrest.spring5mvcrest.api.v1.model.CustomersListDTO;
import com.mvcrest.spring5mvcrest.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomersListDTO> getAllCustomers() {
        return new ResponseEntity<CustomersListDTO>(new CustomersListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }
}
