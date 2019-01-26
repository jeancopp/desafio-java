package br.com.coppieters.concrete.service.maker;

import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMaker {

    public UserDto make(User user) {

        final UserDto dto = UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phones(makerPhone.make(user.getPhones()))
                .build();

        return dto;
    }

    @Autowired
    public UserMaker(UserPhoneMaker makerPhone) {
        this.makerPhone = makerPhone;
    }

    private final UserPhoneMaker makerPhone;
}
