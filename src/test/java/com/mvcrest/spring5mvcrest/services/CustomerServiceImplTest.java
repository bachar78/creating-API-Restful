package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mvcrest.spring5mvcrest.domain.Customer;
import com.mvcrest.spring5mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "Bachar";
    public static final String LAST_NAME = "Daowd";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerMapper = CustomerMapper.INSTANCE;
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(customerDTOS.size(), 3);
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        CustomerDTO customerDTO = customerService.getCustomerById(ID);
        assertEquals(customerDTO.getFirstName(), FIRST_NAME);
        assertEquals(customerDTO.getLastName(), LAST_NAME);
    }

    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Bachar");
        customerDTO.setLastName("Daowd");
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
        assertEquals(savedDto.getFirstName(), savedCustomer.getFirstName());
        assertEquals(savedDto.getCustomerUrl(), "/api/v1/customer/1");
    }
}