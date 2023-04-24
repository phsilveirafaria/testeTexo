package com.br.teste.domain.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MOVIE_PRODUCER")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class MovieProducer {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private MovieProducerId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne
    @MapsId("producerId")
    @JoinColumn(name = "PRODUCER_ID")
    private Producer producer;

}
