package com.br.teste.repository;

import com.br.teste.domain.entity.MovieProducer;
import com.br.teste.domain.entity.MovieProducerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducer, MovieProducerId> {

    List<MovieProducer> findByMovieWinnerTrue();
}
