package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {
    public User register(UserDto dto)
            throws UserRecordedPreviouslyException {
        return null;
    }
}
