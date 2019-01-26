package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.exception.ExpiredSessionException;
import br.com.coppieters.concrete.domain.exception.UnauthorizedTokenException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.UserProfileService;
import br.com.coppieters.concrete.service.maker.UserInformationMaker;
import br.com.coppieters.concrete.util.UserInformatiomMakerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.coppieters.concrete.util.UserConstants.produceUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileApiTest {

    private UserProfileApi api;
    private long userId;
    private String token;
    private UserProfileService service;
    private UserInformationMaker maker;

    @Before
    public void setUp() throws Exception {
        this.service = Mockito.mock(UserProfileService.class);
        this.maker = UserInformatiomMakerFactory.produce();
        this.api = new UserProfileApi(service, maker);
        this.userId = 10L;
        this.token = "TOKEN";
    }

    @Test
    public void WithTokenAndUserId_OnSearch_ReturnValue(){
        final User user = produceUser();

        doReturn(user)
                .when(service)
                .getUserInformation(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(token));

        ResponseEntity<?> response = api.getUser(this.userId, this.token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void WithTokenExpiredAndUserId_OnSearch_ReturnMessageAboutIt() throws Exception {
        final String MESSAGE = "Sessão invalida";

        doThrow(new ExpiredSessionException(MESSAGE))
                .when(service)
                .validate(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(token) );

        testWhenThrowException(MESSAGE);
    }


    @Test
    public void WithUnautorizathedToken_OnSearch_ReturnMessageAboutIt() throws Exception {
        String MESSAGE = "Não autorizado";

        doThrow(new UnauthorizedTokenException(MESSAGE))
                .when(service)
                .verify( ArgumentMatchers.eq(token) );

        testWhenThrowException(MESSAGE);
    }

    private void testWhenThrowException(String message) {
        ResponseEntity<?> response = api.getUser(this.userId, this.token);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        ErrorMessageDto body = (ErrorMessageDto) response.getBody();
        assertEquals(message, body.getMessage());
    }


}