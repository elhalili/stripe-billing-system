package com.elhalili.stripebillingsystem.repository;

import com.elhalili.stripebillingsystem.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
