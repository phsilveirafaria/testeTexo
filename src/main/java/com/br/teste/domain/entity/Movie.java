package com.br.teste.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOVIE")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@With
public class Movie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "RELEASE_YEAR", nullable = false)
    private Long year;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "WINNER", nullable = false)
    private Boolean winner;

    @ManyToMany
    @JoinTable(
            name = "MOVIE_PRODUCER",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCER_ID"))
    private Set<Producer> producers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "MOVIE_STUDIO",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDIO_ID"))
    private Set<Studio> studios = new HashSet<>();

}
