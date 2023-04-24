package com.br.teste.domain.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class MinAndMaxPrizeResponse {

    private Set<ProducerResponse> min;
    private Set<ProducerResponse> max;
}
