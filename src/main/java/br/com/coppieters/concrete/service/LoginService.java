package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public User login(LoginDto dto)
        throws UserNotFoundException, InvalidUserDataException {
        return null;
    }
}
