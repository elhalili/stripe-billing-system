package com.elhalili.stripebillingsystem.web;

import com.elhalili.stripebillingsystem.dto.ClientDto;
import com.elhalili.stripebillingsystem.dto.PlanDto;
import com.elhalili.stripebillingsystem.dto.SubscriptionRequest;
import com.elhalili.stripebillingsystem.service.BillingService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BillingController {
    private BillingService billingService;
    @GetMapping("/clients")
    public List<ClientDto> listClient() {
        return billingService.listClients();
    }
    @PostMapping("/client-register")
    public ResponseEntity<Object> customerRegister(@RequestBody ClientDto clientDto) throws StripeException {
        System.out.println(clientDto.toString());
        ClientDto client = billingService.clientRegister(clientDto);
        return ResponseEntity.ok(client);
    }
    @PostMapping("/create-subscription")
    public ResponseEntity<Object> createSubscription(@RequestBody SubscriptionRequest subscriptionDto) throws StripeException {
        billingService.createSubscription(subscriptionDto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/create-plan")
    public ResponseEntity<PlanDto> createPlan(@RequestBody PlanDto planDto) throws StripeException {
        return ResponseEntity.ok(billingService.createPlan(planDto));
    }
}
