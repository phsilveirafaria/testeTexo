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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

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

                    var producerIntervals = generateProducerIntervals(producer, movies);

                    for(var producerResponse : producerIntervals) {
                        if(validatedInterval.containsKey(producerResponse.getInterval())) {
                            validatedInterval.get(producerResponse.getInterval()).add(producerResponse);
                        } else {
                            var producerResponseList = new HashSet<ProducerResponse>();
                            producerResponseList.add(producerResponse);
                            validatedInterval.put(producerResponse.getInterval(), producerResponseList);
                        }
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

    private List<ProducerResponse> generateProducerIntervals(Producer producer,  List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getYear));
        return range(0, movies.size() - 1)
                .mapToObj(index -> {
                    var interval = movies.get(index + 1).getYear() - movies.get(index).getYear();
                    return ProducerResponse.builder()
                            .producer(producer.getName())
                            .interval(interval)
                            .previousWin(movies.get(index).getYear())
                            .followingWin(movies.get(index + 1).getYear())
                            .build();
                }).collect(Collectors.toList());
    }
}
