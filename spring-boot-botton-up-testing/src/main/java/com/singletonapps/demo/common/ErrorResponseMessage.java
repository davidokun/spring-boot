package com.singletonapps.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseMessage {

    private int status;
    private String message;
}
