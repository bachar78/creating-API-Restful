package com.mvcrest.spring5mvcrest.bootstrap;

import com.mvcrest.spring5mvcrest.domain.Category;
import com.mvcrest.spring5mvcrest.domain.Customer;
import com.mvcrest.spring5mvcrest.domain.Vendor;
import com.mvcrest.spring5mvcrest.repositories.CategoryRepository;
import com.mvcrest.spring5mvcrest.repositories.CustomerRepository;
import com.mvcrest.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;


    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

        System.out.println("Vendors Data is Loaded = " + vendorRepository.count());
    }

    private void loadCustomers() {
        Customer bachar = new Customer();
        bachar.setFirstName("Bachar");
        bachar.setLastName("Daowd");

        Customer samer = new Customer();
        samer.setFirstName("Samer");
        samer.setLastName("Najjar");

        Customer bassam = new Customer();
        bassam.setFirstName("Bassam");
        bassam.setLastName("Machoul");

        Customer laila = new Customer();
        laila.setFirstName("Laila");
        laila.setLastName("Yazjy");

        Customer lama = new Customer();
        lama.setFirstName("Lama");
        lama.setLastName("Gharib");

        customerRepository.save(bachar);
        customerRepository.save(samer);
        customerRepository.save(bassam);
        customerRepository.save(laila);
        customerRepository.save(lama);

        System.out.println("Data Customer Loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Category Loaded = " + categoryRepository.count());
    }
}
