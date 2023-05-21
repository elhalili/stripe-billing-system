package com.elhalili.stripebillingsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String stripeId;
    private String name;
    @Column(unique = true)
    private String email;
    @ManyToOne
    private Plan plan;
}
