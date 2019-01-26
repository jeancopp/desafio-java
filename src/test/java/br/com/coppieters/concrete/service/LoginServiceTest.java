package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.util.UserConstants;
import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Function;

import static br.com.coppieters.concrete.util.UserConstants.CRIPT_PASSWORD;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    private LoginService service;
    private UserRepository repository;
    private LoginDto dto;
    private Function<String, String> passwordEncripter;
    private final User user = UserConstants.produceUser();

    @Before
    public void setUp() throws Exception {
        this.passwordEncripter = Mockito.mock(Function.class);
        this.repository = Mockito.mock(UserRepository.class);

        this.service = new LoginService(repository, passwordEncripter);

        this.dto = LoginDto.builder()
                .login("login")
                .password("password")
                .build();
    }

    @Test
    public void withLoginAndPassword_OnTryToLogin_LoginWithSucess() throws UserNotFoundException, InvalidUserDataException {
        user.setPassword(CRIPT_PASSWORD);

        doReturn(Optional.of(user))
                .when(repository)
                .findByEmail(ArgumentMatchers.eq(dto.getLogin()));

        doReturn(UserConstants.CRIPT_PASSWORD)
                .when(passwordEncripter)
                .apply(ArgumentMatchers.eq(dto.getPassword()));

        final User loggedUser = service.login(this.dto);
        assertEquals(user, loggedUser);
    }


    @Test(expected = UserNotFoundException.class)
    public void withLoginAndPassword_OnTryToLogin_DontFoundUserOnDatabase() throws UserNotFoundException, InvalidUserDataException {
        final User user = UserConstants.produceUser();
        user.setPassword(CRIPT_PASSWORD);

        doReturn(Optional.empty())
                .when(repository)
                .findByEmail(ArgumentMatchers.eq(dto.getLogin()));
        service.login(this.dto);

    }

    @Test(expected = InvalidUserDataException.class)
    public void withLoginAndPassword_OnTryToLogin_PasswordIsDifferentFromExpected() throws UserNotFoundException, InvalidUserDataException {
        final User user = UserConstants.produceUser();
        user.setPassword(CRIPT_PASSWORD);

        doReturn(Optional.of(user))
                .when(repository)
                .findByEmail(ArgumentMatchers.eq(dto.getLogin()));

        doReturn(UserConstants.PASSWORD)
                .when(passwordEncripter)
                .apply(ArgumentMatchers.eq(dto.getPassword()));

        service.login(this.dto);
    }


}