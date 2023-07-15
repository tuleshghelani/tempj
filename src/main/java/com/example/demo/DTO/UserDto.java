package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
//@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private String role;
    private String password;

    public UserDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

}
