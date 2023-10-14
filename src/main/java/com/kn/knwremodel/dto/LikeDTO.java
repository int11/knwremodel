package com.kn.knwremodel.dto;

import lombok.Getter;

public class LikeDTO {
    @Getter
    public static class Request{
        private String user;
        private Long noticeId;
    }
}
