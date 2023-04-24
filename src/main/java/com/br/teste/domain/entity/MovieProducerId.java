package com.br.teste.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MovieProducerId {

    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "PRODUCER_ID")
    private Long producerId;
}
