package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mvcrest.spring5mvcrest.controllers.v1.exceptionHandler.ApiRequestException;
import com.mvcrest.spring5mvcrest.domain.Customer;
import com.mvcrest.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = customerRepository.findAll()
                .stream().map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
        return customers;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
//        return customerMapper.customerToCustomerDTO(customerRepository.findById(id).get());
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + id);
                    return customerDTO;
                })
                .orElseThrow(()-> new ApiRequestException("Customer was not found"));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDto = customerMapper.customerToCustomerDTO(savedCustomer);
        returnedCustomerDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());
        return returnedCustomerDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnedCustomerDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
        return returnedCustomerDTO;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }
            CustomerDTO returnedCustomer = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnedCustomer.setCustomerUrl("/api/v1/customers/" + id);
            return returnedCustomer;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
