package com.example.demo.controller;

import com.example.demo.data.Film;
import com.example.demo.data.Log;
import com.example.demo.dto.FilmDto;
import com.example.demo.enums.ChangeTypeEnum;
import com.example.demo.enums.TablesEnum;
import com.example.demo.jms.Sender;
import com.example.demo.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/film")
public class FilmController {
    private final FilmService filmService;
    private final Sender sender;

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("film", new FilmDto());
        return "all-films";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("film", filmService.findById(id));
        return "update-film";
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable("id") Long id, @ModelAttribute FilmDto filmDto, Model model) {
        filmDto.setId(id);
        filmService.save(filmDto);
        sender.send(
                Log.builder()
                        .changeType(ChangeTypeEnum.UPDATE)
                        .table(TablesEnum.FILMS)
                        .value(filmDto.toEntity().toString())
                        .datetime(LocalDateTime.now())
                        .build()
        );
        return "redirect:/film";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Film film = filmService.deleteById(id);
        sender.send(
                Log.builder()
                        .changeType(ChangeTypeEnum.CASCADE_DELETE)
                        .table(TablesEnum.FILMS)
                        .value(film.toString())
                        .datetime(LocalDateTime.now())
                        .build()
        );
        return "redirect:/film";
    }

    @PostMapping("")
    public String create(@ModelAttribute FilmDto filmDto, Model model) {
        filmService.save(filmDto);
        sender.send(
                Log.builder()
                        .changeType(ChangeTypeEnum.INSERT)
                        .table(TablesEnum.FILMS)
                        .value(filmDto.toEntity().toString())
                        .datetime(LocalDateTime.now())
                        .build()
        );
        return "redirect:/film";
    }


}
