package com.elhalili.stripebillingsystem.dto;

import lombok.Data;

public @Data class SubscriptionRequest {
    private String priceId;
    private String customerId;
}
