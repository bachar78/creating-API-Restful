package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.domain.Vendor;
import com.mvcrest.spring5mvcrest.repositories.VendorRepository;
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
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    public static final String NAME = "Bachar";
    public static final long ID = 1L;
    VendorService vendorService;

@Mock
VendorRepository vendorRepository;

VendorMapper vendorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorMapper = VendorMapper.INSTANCE;
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(anyLong());
        assertEquals(vendorDTO.getName(), NAME);
    }



    @Test
    void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        assertEquals(vendorDTOS.size(), 3);
    }

    @Test
    void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(1L);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO returnedVendorDTO = vendorService.createNewVendor(vendorDTO);
        assertEquals(returnedVendorDTO.getName(), vendor.getName());
        assertEquals(returnedVendorDTO.getVendorUrl(), "/api/v1/vendor/1" );
    }

    @Test
    void saveVendorByDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl("/api/v1/vendor/" + ID);
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(vendorDTO.getName());
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO returnedVendorDTO = vendorService.saveVendorByDTO(ID, vendorDTO);
        assertEquals(returnedVendorDTO.getName(), vendor.getName());
        assertEquals(returnedVendorDTO.getVendorUrl(), vendorDTO.getVendorUrl());
    }

    @Test
    void patchVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl("/api/v1/vendor/1");
        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(ID);
//        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
//        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        VendorDTO returnedVendorDTO = vendorService.patchVendor(ID, vendorDTO);
        assertEquals(returnedVendorDTO.getName(), vendor.getName());
        assertEquals(returnedVendorDTO.getVendorUrl(), vendorDTO.getVendorUrl());
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}