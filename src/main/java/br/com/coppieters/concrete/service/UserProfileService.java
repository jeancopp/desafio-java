package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.exception.ExpiredSessionException;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UnauthorizedTokenException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UserProfileService {

    public static final Supplier<UnauthorizedTokenException> NAO_AUTORIZADO = () -> new UnauthorizedTokenException("Não autorizado");

    public void verify(String token)
                throws UnauthorizedTokenException {
        Optional<User> user = repository.findByToken(token);
        user.orElseThrow(NAO_AUTORIZADO);
    }

    public User getUserInformation(long userId, String token) {
        Optional<User> user = repository.findById(userId);

        return user.get();
    }

    public void validate(long userId, String token)
            throws ExpiredSessionException, InvalidUserDataException, UnauthorizedTokenException {

        Optional<User> possibleUser = repository.findById(userId);
        User user = possibleUser.orElseThrow(() -> new InvalidUserDataException("Usuário nao encontrado"));

        if(!token.equals(user.getToken())){
            UnauthorizedTokenException exception = NAO_AUTORIZADO.get();
            throw exception;
        }

        long differenceBetweenLastLoginAndNow = ChronoUnit.MINUTES.between(LocalDateTime.now(), user.getLastLogin());
        if(differenceBetweenLastLoginAndNow > 30){
            throw new ExpiredSessionException("Sessão inválida");
        }

    }

    @Autowired
    public UserProfileService(UserRepository repository) {
        this.repository = repository;
    }

    private final UserRepository repository;
}
