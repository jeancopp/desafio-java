package br.com.coppieters.concrete.domain.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@ToString
@EqualsAndHashCode
public class PhoneDto implements Serializable {

    private String number;
    private String ddd;
}
