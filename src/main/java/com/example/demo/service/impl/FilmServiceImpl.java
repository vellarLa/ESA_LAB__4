package com.example.demo.service.impl;


import com.example.demo.data.Film;
import com.example.demo.data.Log;
import com.example.demo.dto.FilmDto;
import com.example.demo.enums.ChangeTypeEnum;
import com.example.demo.enums.TablesEnum;
import com.example.demo.jms.Sender;
import com.example.demo.repository.FilmRepository;
import com.example.demo.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public void save(FilmDto filmDto) {
        filmRepository.save(filmDto.toEntity());
    }

    @Override
    public Film deleteById(Long id) {
        Film film = filmRepository.findFirstById(id);
        filmRepository.deleteById(id);
        return film;
    }

    @Override
    public FilmDto findById(Long id) {
        Film film = filmRepository.findFirstById(id);
        return film == null ? null : film.toDto();
    }

    @Override
    public List<FilmDto> findAll() {
        return filmRepository.findAll().stream().map(Film::toDto).collect(Collectors.toList());
    }
}
