package com.project.tempotalk.payload.response;

import com.project.tempotalk.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private User user = null;
    private String message;

    public AuthResponse(String message){
        this.message = message;
    }
}
