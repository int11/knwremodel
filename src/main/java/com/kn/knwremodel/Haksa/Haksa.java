package com.kn.knwremodel.Haksa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
public class Haksa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date_start;
    private String date_end;
    private String schedule;

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setDateStart(String dateStart) {
        this.date_start = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.date_end = dateEnd;
    }
}
