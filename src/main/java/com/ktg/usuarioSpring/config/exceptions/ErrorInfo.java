package com.ktg.usuarioSpring.config.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorInfo {

    private String error;
    private int statusCode;
    private String urlRequested;
}
