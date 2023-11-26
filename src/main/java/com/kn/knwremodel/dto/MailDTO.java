package com.kn.knwremodel.dto;

import lombok.Getter;

public class MailDTO {
    @Getter
    public static class send{
        private String mail;
    }

    @Getter
    public static class confirmNumber{
        private String enteredNumber;
    }
}

