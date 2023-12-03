package com.kn.knwremodel.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;

public class ScholarshipDTO {

    @Getter
    public static class Scholarship{
        private Long id;
        private LocalDate regdate;
        private List<String> columns;

        public Scholarship(com.kn.knwremodel.entity.Scholarship scholarship) {
            this.id = scholarship.getId();
            this.regdate = scholarship.getRegdate();
            this.columns = scholarship.getColumns();
        }
    }

    @Getter
    public static class save{
        private Long scholarshipId;
        private List<String> columns;
    }
}

