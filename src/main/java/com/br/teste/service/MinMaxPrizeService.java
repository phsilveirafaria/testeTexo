package com.br.teste.service;

import com.br.teste.domain.entity.Movie;
import com.br.teste.domain.entity.MovieProducer;
import com.br.teste.domain.entity.Producer;
import com.br.teste.domain.response.MinAndMaxPrizeResponse;
import com.br.teste.domain.response.ProducerResponse;
import com.br.teste.exception.MinAndMaxPrizeException;
import com.br.teste.repository.MovieProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MinMaxPrizeService {

    private final MovieProducerRepository movieProducerRepository;

    public MinAndMaxPrizeResponse run() {
        var producerMovies = findProducersMoviesWinner();

        var validatedInterval = new TreeMap<Long, HashSet<ProducerResponse>>();

        producerMovies.entrySet().stream()
                .filter(producerSetEntry -> producerSetEntry.getValue().size() > 1)
                .forEach(producerSetEntry -> {
                    var producer = producerSetEntry.getKey();
                    var movies = producerSetEntry.getValue();
                    movies.sort(Comparator.comparing(Movie::getYear));

                    var interval = generateInterval(movies);
                    var producerResponse = ProducerResponse.builder()
                            .producer(producer.getName())
                            .interval(interval)
                            .previousWin(movies.get(0).getYear())
                            .followingWin(movies.get(movies.size() - 1).getYear())
                            .build();

                    if(validatedInterval.containsKey(interval)) {
                        validatedInterval.get(interval).add(producerResponse);
                    } else {
                        var producerResponseList = new HashSet<ProducerResponse>();
                        producerResponseList.add(producerResponse);
                        validatedInterval.put(interval, producerResponseList);
                    }
                });

        if(validatedInterval.size() == 0) {
            throw new MinAndMaxPrizeException();
        }

        return MinAndMaxPrizeResponse.builder()
                .min(validatedInterval.firstEntry().getValue())
                .max(validatedInterval.lastEntry().getValue())
                .build();
    }

    private Map<Producer, List<Movie>> findProducersMoviesWinner() {
        var winnerMovies = movieProducerRepository.findByMovieWinnerTrue();
        return winnerMovies.stream()
                .collect(groupingBy(MovieProducer::getProducer, mapping(MovieProducer::getMovie, toList())));
    }

    private Long generateInterval(List<Movie> movies) {
        return movies.get(movies.size() - 1).getYear() - movies.get(0).getYear();
    }
}
