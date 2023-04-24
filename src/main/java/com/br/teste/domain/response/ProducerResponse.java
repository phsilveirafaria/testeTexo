package com.br.teste.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProducerResponse {

    private String producer;
    private Long interval;
    private Long previousWin;
    private Long followingWin;
}
