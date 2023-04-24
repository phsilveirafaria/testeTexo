package com.br.teste.controller;

import com.br.teste.domain.response.MinAndMaxPrizeResponse;
import com.br.teste.service.MinMaxPrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final MinMaxPrizeService minMaxPrizeService;

    @GetMapping("/prize/min-max")
    public MinAndMaxPrizeResponse getMinAndMaxPrize() {
        return minMaxPrizeService.run();
    }
}
