package com.elhalili.stripebillingsystem.service;

import com.elhalili.stripebillingsystem.dto.ClientDto;
import com.elhalili.stripebillingsystem.dto.PlanDto;
import com.elhalili.stripebillingsystem.dto.SubscriptionRequest;
import com.stripe.exception.StripeException;

import java.util.List;

public interface BillingService {
    ClientDto clientRegister(ClientDto clientDto) throws StripeException;
    PlanDto createPlan(PlanDto planDto) throws StripeException;
    List<ClientDto> listClients();
    List<PlanDto> listPlans();
    void createSubscription(SubscriptionRequest subscriptionDto) throws StripeException;
}
