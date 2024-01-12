package com.example.demo.data;

import com.example.demo.dto.TimetableDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "timetable", schema = "lab2")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Timetable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Film.class, fetch = FetchType.EAGER, optional = false, cascade={CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "hall")
    private int hall;

    @Column(name = "date")
    private LocalDateTime date;

    public TimetableDto toDto() {
        return TimetableDto.builder()
                .id(this.id)
                .film(this.film.toDto())
                .hall(this.hall)
                .date(this.date)
                .build();
    }

    @Override
    public String toString(){
        return "date: " + date + "; hall: " + hall + "; film: (" + film.toString() + ")";
    }
}
