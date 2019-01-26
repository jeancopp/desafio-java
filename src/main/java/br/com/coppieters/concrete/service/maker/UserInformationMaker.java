package br.com.coppieters.concrete.service.maker;

import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInformationMaker {

    public UserInformationDto make(User user){
        UserInformationDto information = UserInformationDto.builder()
                .user(userMaker.make(user))
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .modified(user.getModified())
                .id(user.getId())
                .token(user.getToken())
                .build();

        return information;
    }

    @Autowired
    public UserInformationMaker(UserMaker userMaker) {
        this.userMaker = userMaker;
    }

    private final UserMaker userMaker;
}
