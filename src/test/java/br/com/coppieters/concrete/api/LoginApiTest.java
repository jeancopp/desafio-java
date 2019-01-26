package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.util.UserConstants;
import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.LoginService;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class LoginApiTest {

    private final LoginDto dto = new LoginDto("login", "password");
    private final User user = produceUser();
    private final String INVALID_MESSAGE = "Usuário e/ou senha inválidos";

    private LoginApi api;
    private LoginService service;
    private UserInformationMaker maker;
    @Before
    public void setUp() throws Exception {
        service = Mockito.mock(LoginService.class);
        maker = UserInformatiomMakerFactory.produce();
        api = new LoginApi(service, maker);
    }

    @Test
    public void withLoginAndPasswordOfRecoredUser_OnLogin_ReturnData() throws UserNotFoundException, InvalidUserDataException {
        doReturn(user)
                .when(service)
                .login(ArgumentMatchers.eq(dto));

        ResponseEntity<?> response = api.login(dto);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertTrue(response.hasBody());

        final UserInformationDto body = (UserInformationDto) response.getBody();
        UserConstants.validateUserReturn(body);
    }

    @Test
    public void withCorrectLoginAndWrongPasswordOfRecoredUser_OnLogin_ReturnStatusUnathorized() throws UserNotFoundException, InvalidUserDataException {

        doThrow(new InvalidUserDataException())
                .when(service)
                .login(ArgumentMatchers.eq(dto));

        ResponseEntity<?> response = api.login(dto);
        validateError(response, HttpStatus.UNAUTHORIZED, INVALID_MESSAGE);
    }

    @Test
    public void withIncorrectLoginAndPasswordOfRecoredUser_OnLogin_ReturnStatusNotFound() throws UserNotFoundException, InvalidUserDataException {
        doThrow(new UserNotFoundException())
                .when(service)
                .login(ArgumentMatchers.eq(dto));

        ResponseEntity<?> response = api.login(dto);
        validateError(response, HttpStatus.NOT_FOUND, INVALID_MESSAGE );
    }

    @Test
    public void withDto_OnLoginOccurredInternalError_ReturnStatusInternalServerError() throws UserNotFoundException, InvalidUserDataException {
        doThrow(new RuntimeException())
                .when(service)
                .login(ArgumentMatchers.eq(dto));

        ResponseEntity<?> response = api.login(dto);
        validateError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inexperado");
    }


    private void validateError(ResponseEntity<?> response, HttpStatus status, final String expectedMessage) {
        assertEquals(status, response.getStatusCode());
        assertTrue(response.hasBody());
        ErrorMessageDto message = (ErrorMessageDto) response.getBody();
        assertEquals(expectedMessage, message.getMessage());
    }

}
