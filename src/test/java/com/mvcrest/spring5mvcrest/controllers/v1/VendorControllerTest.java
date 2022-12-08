package com.mvcrest.spring5mvcrest.controllers.v1;

import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.services.ResourceNotFoundException;
import com.mvcrest.spring5mvcrest.services.VendorService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest extends AbstractRestControllerTest {
    private static final Long ID = 1L;
    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorService vendorService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    void getAllVendors() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(new VendorDTO(), new VendorDTO(), new VendorDTO());
        when(vendorService.getAllVendors()).thenReturn(vendors);
        mockMvc.perform(get("/api/v1/vendors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Bachar");
        vendorDTO.setVendorUrl("/api/v1/vendor/1");
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
        mockMvc.perform(get("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Bachar")));
    }

    @Test
    void getVendorByIdNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/api/v1/vendors/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Bachar");
        vendorDTO.setVendorUrl("/api/v1/vendor/1");
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);
        mockMvc.perform(post("/api/v1/vendors").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Bachar")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo("/api/v1/vendor/1")));
    }

    @Test
    void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Bachar");
        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setName(vendorDTO.getName());
        returnedVendor.setVendorUrl("/api/v1/vendor/1");
        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedVendor);
        mockMvc.perform(put("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Bachar")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo("/api/v1/vendor/1")));
    }

    @Test
    void updatePatchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Bachar");
        vendorDTO.setVendorUrl("/api/v1/vendor/1");
        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setName("Bassam");
        returnedVendor.setVendorUrl(vendorDTO.getVendorUrl());
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(returnedVendor);
        mockMvc.perform(patch("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Bassam")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo("/api/v1/vendor/1")));
    }

    @Test
    void deleteVendorById() throws Exception {
        mockMvc.perform(delete("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }
}