package com.br.teste.controller;

import com.br.teste.domain.response.MinAndMaxPrizeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class ProducerControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testMinAndMaxPrizes() throws Exception {
        var url = "http://localhost:" + port + "/producer/prize/min-max";
        var response = testRestTemplate.getForEntity(url, MinAndMaxPrizeResponse.class);
        var body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);

        var min = body.getMin().stream().toList();
        var max = body.getMax().stream().toList();

        assertEquals(1, min.size());
        assertEquals("Joel Silver", min.get(0).getProducer());
        assertEquals(1, min.get(0).getInterval());
        assertEquals(1990, min.get(0).getPreviousWin());
        assertEquals(1991, min.get(0).getFollowingWin());

        assertEquals(1, max.size());
        assertEquals("Matthew Vaughn", max.get(0).getProducer());
        assertEquals(13, max.get(0).getInterval());
        assertEquals(2002, max.get(0).getPreviousWin());
        assertEquals(2015, max.get(0).getFollowingWin());
    }
}