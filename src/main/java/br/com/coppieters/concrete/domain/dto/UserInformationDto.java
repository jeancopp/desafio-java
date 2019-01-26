package br.com.coppieters.concrete.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationDto {

    @JsonIgnoreProperties({"password"})
    private UserDto user;

    private Long id;

    private String token;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

}
