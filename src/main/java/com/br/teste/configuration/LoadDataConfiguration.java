package com.br.teste.configuration;

import com.br.teste.service.LoadDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoadDataConfiguration implements CommandLineRunner {

    private final LoadDataService loadDataService;

    @Override
    public void run(String... args) {
        loadDataService.run();
    }
}
