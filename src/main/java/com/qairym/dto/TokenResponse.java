package com.qairym.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    private String access_token;

    public TokenResponse(String token) {
        this.access_token = token;
    }
}
