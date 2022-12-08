package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO getVendorById(Long id);

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
