package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.domain.exception.ExpiredSessionException;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UnauthorizedTokenException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.repository.UserRepository;
import br.com.coppieters.concrete.util.UserConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static br.com.coppieters.concrete.util.UserConstants.ID;
import static br.com.coppieters.concrete.util.UserConstants.TOKEN;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class UserProfileServiceTest {

    private UserProfileService service;
    private UserRepository repository;
    private User user;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(UserRepository.class);
        service = new UserProfileService(repository);
        user = UserConstants.produceUser();
    }

    @Test
    public void withTokenOfRegisteredUser_OnVerify_DoNothing() throws UnauthorizedTokenException {
        doReturn(Optional.of(user))
                .when(repository)
                .findByToken(ArgumentMatchers.eq(TOKEN));

        service.verify(TOKEN);

        verify(repository, times(1)).findByToken(ArgumentMatchers.eq(TOKEN));
    }

    @Test(expected = UnauthorizedTokenException.class)
    public void withTokenOfRegisteredUser_OnVerify_ThrowExpectedException() throws UnauthorizedTokenException {
        doReturn(Optional.empty())
                .when(repository)
                .findByToken(ArgumentMatchers.eq(TOKEN));

        service.verify(TOKEN);
    }

    @Test(expected = InvalidUserDataException.class)
    public void withDataOfNonRegisteredToken_OnValidate_ThrowInvalidUserDataException() throws Exception {
        doReturn(Optional.empty())
                .when(repository)
                .findById(ArgumentMatchers.eq(ID));

        service.validate(ID, TOKEN);
    }

    @Test(expected = UnauthorizedTokenException.class)
    public void withInvalidToken_OnValidate_ThrowUnauthorizedTokenException() throws Exception {
        doReturn(Optional.of(user))
                .when(repository)
                .findById(ArgumentMatchers.eq(ID));

        service.validate(ID, "TOKEN_ERRADO");
    }

    @Test(expected = ExpiredSessionException.class)
    public void withExpiredToken_OnValidate_ThrowExpiredSessionException() throws Exception {
        user.setLastLogin(LocalDateTime.of(2010, Month.JANUARY, 1, 1,0,0));

        doReturn(Optional.of(user))
                .when(repository)
                .findById(ArgumentMatchers.eq(ID));

        service.validate(ID, TOKEN);
    }

    @Test()
    public void withValidaData_OnValidate_doNothing() throws Exception {
        user.setLastLogin(LocalDateTime.now());

        doReturn(Optional.of(user))
                .when(repository)
                .findById(ArgumentMatchers.eq(ID));

        service.validate(ID, TOKEN);

        verify(repository, times(1)).findById(ArgumentMatchers.eq(ID));
    }


}
