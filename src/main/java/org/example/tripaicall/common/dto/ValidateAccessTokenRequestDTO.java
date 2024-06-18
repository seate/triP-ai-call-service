package org.example.tripaicall.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateAccessTokenRequestDTO {
    @NotBlank
    private String accessToken;
}