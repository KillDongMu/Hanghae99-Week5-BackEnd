package com.killdongmu.Hanghae99Week5BackEnd.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResDto {

    private String msg;
    private int statusCode;

    public GlobalResDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
