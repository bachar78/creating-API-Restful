package com.mvcrest.spring5mvcrest.controllers.v1;

import com.mvcrest.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mvcrest.spring5mvcrest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    public static final Long ID = 2L;

    @InjectMocks
    CustomerController controller;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        CustomerDTO bachar = new CustomerDTO();
        bachar.setId(1L);
        CustomerDTO samer = new CustomerDTO();
        bachar.setId(2L);
        CustomerDTO bassam = new CustomerDTO();
        bachar.setId(3L);
        List<CustomerDTO> customers = Arrays.asList(bachar, bassam, samer);
        when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(get("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", hasSize(3)));


    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        when(customerService.getCustomerById(ID)).thenReturn(customer);
        mockMvc.perform(get("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(2)));
    }
}