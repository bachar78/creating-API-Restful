package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.bootstrap.Bootstrap;
import com.mvcrest.spring5mvcrest.domain.Vendor;
import com.mvcrest.spring5mvcrest.repositories.CategoryRepository;
import com.mvcrest.spring5mvcrest.repositories.CustomerRepository;
import com.mvcrest.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest

public class VendorServiceImplIt {

    VendorService vendorService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Loading Vendors Data");
        System.out.println(vendorRepository.count());
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);

        //Setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();//load data
    }

    @Test
    public void patchVendorUpdateName() {
        String UpdatedName = "UpdatedName";
        Vendor originalVendor = vendorRepository.findById(getVendorId()).orElseThrow(ResourceNotFoundException::new);
        assertNotNull(originalVendor);
        assertThat(originalVendor.getName(), not(equalTo(UpdatedName)));
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(UpdatedName);
        vendorService.patchVendor(originalVendor.getId(),vendorDTO);
        assertNotNull(vendorRepository.findById(originalVendor.getId()).orElseThrow(ResourceNotFoundException::new));
        assertThat(originalVendor.getName(), equalTo(UpdatedName));
    }
    private Long getVendorId() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.get(0).getId();
    }
}
