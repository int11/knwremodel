package com.kn.knwremodel.Scholarship;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

public class ScholarshipDTO {

    @Getter
    public static class Scholarship{
        private Long id;
        private LocalDate regdate;
        private List<String> columns;

        public Scholarship(com.kn.knwremodel.Scholarship.Scholarship scholarship) {
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

