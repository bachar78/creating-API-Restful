package com.mvcrest.spring5mvcrest.repositories;

import com.mvcrest.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByName(String name);
}
