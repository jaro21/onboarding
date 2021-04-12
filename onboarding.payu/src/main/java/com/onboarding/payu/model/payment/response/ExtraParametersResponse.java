package com.onboarding.payu.model.payment.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExtraParametersResponse {
    @JsonProperty("BANK_REFERENCED_CODE")
    private String bankReferencedCode;
}
