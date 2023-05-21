package com.elhalili.stripebillingsystem.service;

import com.elhalili.stripebillingsystem.dto.ClientDto;
import com.elhalili.stripebillingsystem.dto.PlanDto;
import com.elhalili.stripebillingsystem.dto.SubscriptionRequest;
import com.elhalili.stripebillingsystem.entity.Client;
import com.elhalili.stripebillingsystem.entity.Plan;
import com.elhalili.stripebillingsystem.repository.ClientRepository;
import com.elhalili.stripebillingsystem.repository.PlanRepository;
import com.elhalili.stripebillingsystem.repository.SubscriptionRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BillingServiceImpl implements BillingService {
    private ModelMapper modelMapper;
    private ClientRepository clientRepository;
    private PlanRepository planRepository;
    private SubscriptionRepository subscriptionRepository;
    @Override
    public ClientDto clientRegister(ClientDto clientDto) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                        .setEmail(clientDto.getEmail())
                        .setName(clientDto.getName())
                        .build();

        Customer customer = Customer.create(params);
        Client client = modelMapper.map(clientDto, Client.class);
        client.setStripeId(customer.getId());
        Client client1 = clientRepository.save(client);

        return modelMapper.map(client1, ClientDto.class);
    }

    @Override
    public PlanDto createPlan(PlanDto planDto) throws StripeException {
        ProductCreateParams params = ProductCreateParams.builder()
                .setName(planDto.getName())
                .build();

        Product product = Product.create(params);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setProduct(product.getId())
                .setUnitAmount((long)((planDto.getPrice() / 10) * 10))
                .setCurrency("usd")
                .setRecurring(PriceCreateParams.Recurring.builder()
                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                        .build())
                .build();

        Price price = Price.create(priceParams);
        planDto.setProductId(product.getId());
        planDto.setPriceId(price.getId());

        Plan plan = planRepository.save(modelMapper.map(planDto, Plan.class));
        return modelMapper.map(plan, PlanDto.class);
    }

    @Override
    public List<ClientDto> listClients() {
        return clientRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanDto> listPlans() {
        return planRepository.findAll().stream()
                .map(e -> modelMapper.map(e, PlanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createSubscription(SubscriptionRequest subscriptionRequest) throws StripeException {


        // Automatically save the payment method to the subscription
        // when the first payment is successful.
        SubscriptionCreateParams.PaymentSettings paymentSettings =
                SubscriptionCreateParams.PaymentSettings
                        .builder()
                        .setSaveDefaultPaymentMethod(SubscriptionCreateParams.PaymentSettings.SaveDefaultPaymentMethod.ON_SUBSCRIPTION)
                        .build();

        // Create the subscription. Note we're expanding the Subscription's
        // latest invoice and that invoice's payment_intent
        // , so we can pass it to the front end to confirm the payment
        SubscriptionCreateParams subCreateParams = SubscriptionCreateParams
                .builder()
                .setCustomer(subscriptionRequest.getCustomerId())
                .addItem(
                        SubscriptionCreateParams
                                .Item.builder()
                                .setPrice(subscriptionRequest.getPriceId())
                                .build()
                )
                .setPaymentSettings(paymentSettings)
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                .addAllExpand(List.of("latest_invoice.payment_intent"))
                .build();

        Subscription subscription = Subscription.create(subCreateParams);

        com.elhalili.stripebillingsystem.entity.Subscription subs = com.elhalili.stripebillingsystem.entity.Subscription.builder()
                .createdAt(new Date())
                .status(subscription.getStatus())
                .stripeId(subscription.getId())
                .build();

        subscriptionRepository.save(subs);
    }
}
