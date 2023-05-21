package com.elhalili.stripebillingsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public @Data class Plan {
    @Id
    @GeneratedValue
    private Long id;
    private String priceId;
    private String productId;
    private String name;
    private Float price;
    @OneToMany
    private List<Client> clients;
}
