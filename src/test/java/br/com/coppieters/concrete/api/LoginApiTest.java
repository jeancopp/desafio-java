package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.UserConstants;
import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.LoginService;
import br.com.coppieters.concrete.service.maker.UserMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.coppieters.concrete.UserConstants.produceUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LoginApiTest {

    private LoginApi api;
    private LoginService service;
    private UserMaker maker;
    @Before
    public void setUp() throws Exception {
        service = Mockito.mock(LoginService.class);
        maker = Mockito.mock(UserMaker.class);
        api = new LoginApi(service, maker);
    }

    @Test
    public void withLoginAndPasswordOfRecoredUser_OnLogin_ReturnData() throws UserNotFoundException, InvalidUserDataException {
        final LoginDto dto = new LoginDto("login", "password");
        final User user = produceUser();

        doReturn(user)
                .when(service)
                .login(ArgumentMatchers.eq(dto));

        ResponseEntity<?> response = api.login(dto);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertTrue(response.hasBody());

        final UserInformationDto body = (UserInformationDto) response.getBody();
        UserConstants.validateUserReturn(body);
    }

}
