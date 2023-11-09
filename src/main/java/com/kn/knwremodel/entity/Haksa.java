package com.kn.knwremodel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

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
