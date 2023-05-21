package com.elhalili.stripebillingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class PlanDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String priceId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String productId;
    private String name;
    private Float price;
}
