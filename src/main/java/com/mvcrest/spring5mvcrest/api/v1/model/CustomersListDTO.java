package com.mvcrest.spring5mvcrest.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomersListDTO {
    private List<CustomerDTO> customers;
}
