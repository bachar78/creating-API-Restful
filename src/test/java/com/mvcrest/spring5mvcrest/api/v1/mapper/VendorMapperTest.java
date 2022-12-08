package com.mvcrest.spring5mvcrest.api.v1.mapper;

import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

VendorMapper vendorMapper = VendorMapper.INSTANCE;
    public static final String NAME = "I am a vendor for testing";
    public static final Long ID = 1L;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        assertEquals(vendorDTO.getName(), NAME);
    }

    @Test
    void vendorDTOtoVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        assertEquals(vendor.getName(), NAME);
    }
}