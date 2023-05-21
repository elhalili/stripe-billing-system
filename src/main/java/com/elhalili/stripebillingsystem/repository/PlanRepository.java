package com.elhalili.stripebillingsystem.repository;

import com.elhalili.stripebillingsystem.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
