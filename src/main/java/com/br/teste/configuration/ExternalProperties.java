package com.br.teste.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
@AllArgsConstructor
@Getter
public class ExternalProperties {

    private String filename;
}
