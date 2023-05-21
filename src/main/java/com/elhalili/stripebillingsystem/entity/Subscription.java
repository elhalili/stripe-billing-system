package com.elhalili.stripebillingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    private String stripeId;
    @OneToOne
    private Client client;
    private Date createdAt;
    private String status;
}
