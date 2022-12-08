package com.mvcrest.spring5mvcrest.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
}
