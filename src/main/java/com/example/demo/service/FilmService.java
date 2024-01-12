package com.example.demo.service;

import com.example.demo.data.Film;
import com.example.demo.dto.FilmDto;

import java.util.List;

public interface FilmService {
    void save(FilmDto filmDto);
    Film deleteById(Long id);
    FilmDto findById(Long id);
    List<FilmDto> findAll();

}
