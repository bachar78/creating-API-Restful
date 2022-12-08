package com.mvcrest.spring5mvcrest.controllers.v1;

import com.mvcrest.spring5mvcrest.api.v1.model.VendorDTO;
import com.mvcrest.spring5mvcrest.api.v1.model.VendorListDTO;
import com.mvcrest.spring5mvcrest.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long vendorId) {
        return vendorService.getVendorById(vendorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long vendorId) {
        return vendorService.saveVendorByDTO(vendorId, vendorDTO);
    }

    @PatchMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updatePatchVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long vendorId) {
        return vendorService.patchVendor(vendorId, vendorDTO);
    }

    @DeleteMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable Long vendorId) {
        vendorService.deleteVendorById(vendorId);
    }
}
