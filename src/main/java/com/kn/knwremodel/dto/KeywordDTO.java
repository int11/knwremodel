package com.kn.knwremodel.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
