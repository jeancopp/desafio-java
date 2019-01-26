package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Service
public class LoginService {

    @Transactional
    public User login(LoginDto dto)
        throws UserNotFoundException, InvalidUserDataException {

        Optional<User> possibleUser = repository.findByEmail(dto.getLogin());
        if(!possibleUser.isPresent()){
            throw new UserNotFoundException();
        }

        final User user = possibleUser.get();
        final String encryptedPassword = passwordEncrypter.apply(dto.getPassword());

        if(!encryptedPassword.equals(user.getPassword())){
            throw new InvalidUserDataException();
        }

        LocalDateTime newLastLoginTime = LocalDateTime.now();
        user.setLastLogin(newLastLoginTime);
        repository.save(user);

        return user;
    }

    @Autowired
    public LoginService(UserRepository repository, @Qualifier("PasswordEncrypter") Function<String, String> passwordEncrypter) {
        this.repository = repository;
        this.passwordEncrypter = passwordEncrypter;
    }

    private final UserRepository repository;
    private final Function<String, String> passwordEncrypter;
}
