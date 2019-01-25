package br.com.coppieters.concrete.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable{

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private List<PhoneDto> phones;

}
