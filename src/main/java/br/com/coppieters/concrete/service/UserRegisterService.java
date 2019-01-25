package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.dto.PhoneDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.domain.model.UserPhone;
import br.com.coppieters.concrete.repository.UserRepository;
import br.com.coppieters.concrete.service.maker.UserPhoneMaker;
import lombok.extern.jbosslog.JBossLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@JBossLog
public class UserRegisterService {

    @Transactional
    public User register(UserDto dto)
            throws UserRecordedPreviouslyException {

        log.trace("Search for user with this e-mail");
        Optional<User> possibleUser = this.repository.findByEmail(dto.getEmail());

        if(possibleUser.isPresent()){
            log.info("User with this e-mail exists in database");
            throw new UserRecordedPreviouslyException("E-mail já existente");
        }

        log.trace("Transform dto for user");
        final LocalDateTime now = LocalDateTime.now();

        final User user = User.builder()
                .lastLogin(now)
                .created(now)
                .modified(now)
                .name(dto.getName())
                .email(dto.getEmail())
                .phones(phoneMaker.make(dto))
                .build();
        user.getPhones().forEach( p -> p.setUser(user) );

        String token = tokenGenerator.provide(user);
        user.setToken(token);

        log.trace("Saving user on database");
        final User savedUser = repository.save(user);
        log.debugf("Saved user %s", savedUser.toString());

        return savedUser;
    }

    @Autowired
    public UserRegisterService(UserRepository repository, UserPhoneMaker phoneMaker, TokenGeneratorService tokenGenerator) {
        this.repository = repository;
        this.phoneMaker = phoneMaker;
        this.tokenGenerator = tokenGenerator;
    }

    private final UserRepository repository;
    private final UserPhoneMaker phoneMaker;
    private final TokenGeneratorService tokenGenerator;
}
