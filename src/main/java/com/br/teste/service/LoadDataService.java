package com.br.teste.service;

import com.br.teste.configuration.ExternalProperties;
import com.br.teste.domain.DataType;
import com.br.teste.domain.entity.Movie;
import com.br.teste.domain.entity.Producer;
import com.br.teste.domain.entity.Studio;
import com.br.teste.repository.MovieRepository;
import com.br.teste.repository.ProducerRepository;
import com.br.teste.repository.StudioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;

import static com.br.teste.domain.DataType.PRODUCER;
import static com.br.teste.domain.DataType.STUDIO;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadDataService {

    private final static Character CSV_DELIMITER = ';';
    private final static String DATA_DELIMITER = ",| and ";
    private final static String WINNER_VALUE = "yes";


    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final StudioRepository studioRepository;
    private final ExternalProperties externalProperties;


    @Transactional
    public void run() {
        var csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(CSV_DELIMITER)
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        var inputStream = getDataInputStream(externalProperties.getFilename());

        try (var records = CSVParser.parse(new InputStreamReader(inputStream, UTF_8), csvFormat)) {
            for (var record : records) {
                var year = record.get("year");
                var title = record.get("title");
                var studios = record.get("studios");
                var producers = record.get("producers");
                var winner = record.get("winner");

                var movie = saveMovie(year, title, winner);
                saveProducersAndStudios(movie, producers, studios);
            }
        } catch (Exception ex) {
            log.error("Ocorreu um erro ao carregar os dados do CSV.", ex);
        }
    }

    private Movie saveMovie(String year, String title, String winner) {
        var winnerValidation = hasText(winner) && WINNER_VALUE.equals(winner);
        var movie = new Movie()
                .withYear(Long.valueOf(year))
                .withTitle(title)
                .withWinner(winnerValidation);
        return movieRepository.save(movie);
    }

    private void saveProducersAndStudios(Movie movie, String producers, String studios) {
        saveData(movie, producers, PRODUCER);
        saveData(movie, studios, STUDIO);
        movieRepository.save(movie);
    }

    private void saveData(Movie movie, String data, DataType type) {
        for(var name : data.split(DATA_DELIMITER)) {
            if(hasText(name)) {
                var formattedName = name.strip();
                if(PRODUCER.equals(type)) {
                    var producerOptional = producerRepository.findByName(formattedName);
                    var producer = producerOptional.orElseGet(() ->
                            producerRepository.save(new Producer().withName(formattedName)));
                    movie.getProducers().add(producer);
                } else {
                    var studioOptional = studioRepository.findByName(formattedName);
                    var studio = studioOptional.orElseGet(() ->
                            studioRepository.save(new Studio().withName(formattedName)));
                    movie.getStudios().add(studio);
                }
            }
        }
    }

    private InputStream getDataInputStream(String filename) {
        var classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(filename);
    }
}
