package br.com.coppieters.concrete.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable{

    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;

}
