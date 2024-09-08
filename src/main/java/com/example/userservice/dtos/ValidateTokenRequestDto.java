package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import lombok.Data;

@Data
public class ValidateTokenRequestDto {
    private String  token;
}
