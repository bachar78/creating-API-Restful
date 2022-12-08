package com.mvcrest.spring5mvcrest.services;

import com.mvcrest.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.domain.Vendor;
import com.mvcrest.spring5mvcrest.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl("/api/v1/vendors/" + id);
            return vendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        List<VendorDTO> vendors = vendorRepository.findAll().stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
        return vendors;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendorDTO.setVendorUrl("/api/v1/vendor/" + savedVendor.getId());
        return returnedVendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedVendor = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendor.setVendorUrl("/api/v1/vendors/" + id);
        return returnedVendor;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }
            VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnedVendorDTO.setVendorUrl("/api/v1/vendors" + id);
            return returnedVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
