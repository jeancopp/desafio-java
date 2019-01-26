package br.com.coppieters.concrete.service.maker;

import br.com.coppieters.concrete.domain.dto.PhoneDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.model.UserPhone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserPhoneMaker {

    public List<UserPhone> make(UserDto dto) {
        final List<PhoneDto> phones = Optional.ofNullable(dto.getPhones()).orElse(new ArrayList<>());

        final List<UserPhone> makedPhones = phones.stream()
                                        .map(p -> UserPhone.builder()
                                                    .ddd(p.getDdd())
                                                    .number(p.getNumber())
                                                    .build())
                                        .collect(Collectors.toList());

        return makedPhones;
    }

    public List<PhoneDto> make(List<UserPhone> phones) {
        final List<PhoneDto> dto = phones.stream()
                .map(p -> PhoneDto.builder()
                        .ddd(p.getDdd())
                        .number(p.getNumber())
                        .build())
                .collect(Collectors.toList());

        return dto;
    }
}
