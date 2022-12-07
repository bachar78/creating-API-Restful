package com.mvcrest.spring5mvcrest.bootstrap;

import com.mvcrest.spring5mvcrest.domain.Category;
import com.mvcrest.spring5mvcrest.domain.Customer;
import com.mvcrest.spring5mvcrest.repositories.CategoryRepository;
import com.mvcrest.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
}
