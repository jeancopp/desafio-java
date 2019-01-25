package br.com.coppieters.concrete.service;

import br.com.coppieters.concrete.UserConstants;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.repository.UserRepository;
import br.com.coppieters.concrete.service.maker.UserPhoneMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.coppieters.concrete.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterServiceTest {

    private UserRegisterService service;
    private UserRepository repository;
    private UserPhoneMaker phoneMaker;
    private TokenGeneratorService tokenGenerator;

    @Before
    public void setUp(){
        this.repository = Mockito.mock(UserRepository.class);
        this.phoneMaker = Mockito.mock(UserPhoneMaker.class);
        this.tokenGenerator = Mockito.mock(TokenGeneratorService.class);
        this.service = new UserRegisterService(repository, phoneMaker, tokenGenerator);
    }

    @Test
    public void withUserData_OnTryRegister_RecordIsPersist() throws UserRecordedPreviouslyException {
        final UserDto dto = produceUserDto();
        final User user = produceUser();

        doReturn(TOKEN)
                .when(tokenGenerator)
                .provide(ArgumentMatchers.any(User.class));

        doReturn(new ArrayList<>())
                .when(phoneMaker)
                .make(ArgumentMatchers.eq(dto));

        doReturn(Optional.empty())
                .when(repository)
                .findByEmail(ArgumentMatchers.eq(USER_EMAIL));

        doReturn(user)
                .when(repository)
                .save(ArgumentMatchers.any(User.class));

        final User registeredUser = this.service.register(dto);

        assertEquals(USER_EMAIL, registeredUser.getEmail());
        assertEquals(LAST_LOGIN_DATE, registeredUser.getLastLogin());
        assertEquals(MODIFIER_DATE, registeredUser.getModified() );
        assertEquals(CREATED_DATE, registeredUser.getCreated() );
        assertEquals(TOKEN, registeredUser.getToken());
        assertEquals(ID, registeredUser.getId().longValue());
    }

    @Test(expected = UserRecordedPreviouslyException.class)
    public void withUserPreviouslyRecorded_OnTryRegister_ThrowException() throws UserRecordedPreviouslyException {
        final UserDto dto = produceUserDto();
        final User user = produceUser();

        doReturn(Optional.of(user))
                .when(repository)
                .findByEmail(ArgumentMatchers.eq(USER_EMAIL));

        this.service.register(dto);
    }

}
