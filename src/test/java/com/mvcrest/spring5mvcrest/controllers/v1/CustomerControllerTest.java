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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {
    public static final String FIRST_NAME = "Bachar";

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
        CustomerDTO samer = new CustomerDTO();
        CustomerDTO bassam = new CustomerDTO();
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
        customer.setFirstName(FIRST_NAME);
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);
        mockMvc.perform(get("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Bachar")));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Bachar");
        customer.setLastName("Daowd");

        CustomerDTO returnSavedCustomer = new CustomerDTO();
        returnSavedCustomer.setFirstName(customer.getFirstName());
        returnSavedCustomer.setLastName(customer.getLastName());
        returnSavedCustomer.setCustomerUrl("/api/v1/customer/1");
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnSavedCustomer);

        mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Bachar")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));

    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Bachar");
        customer.setLastName("Daowd");
        CustomerDTO returnSavedCustomer = new CustomerDTO();
        returnSavedCustomer.setFirstName(customer.getFirstName());
        returnSavedCustomer.setLastName(customer.getLastName());
        returnSavedCustomer.setCustomerUrl("/api/v1/customer/1");
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnSavedCustomer);
        mockMvc.perform(put("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Bachar")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));

    }
}
