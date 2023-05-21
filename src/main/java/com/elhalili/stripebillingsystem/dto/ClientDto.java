package com.elhalili.stripebillingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@ToString
public @Data class ClientDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String email;
    private String name;
}
