package com.kn.knwremodel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KeywordDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class request{
        private String keyword;


    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class requestRecentlyKeyword{
        private String keyword;
    }
}
