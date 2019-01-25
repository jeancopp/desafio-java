package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.UserConstants;
import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.UserRegisterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.coppieters.concrete.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterApiTest {

    private UserRegisterApi api;
    private UserRegisterService service;
    private UserDto dto;

    @Before
    public void setUp(){
        service = Mockito.mock(UserRegisterService.class);
        api = new UserRegisterApi(service);
        dto = produceUserDto();
    }

    @Test
    public void withRightData_OnRecord_ShouldRecord() throws UserRecordedPreviouslyException {
        final User recordedUser = produceUser();

        doReturn(recordedUser)
            .when(service)
            .register(ArgumentMatchers.eq(dto));

        ResponseEntity<?> returnValue = api.register(dto);

        assertEquals(HttpStatus.CREATED, returnValue.getStatusCode());
        assertTrue( returnValue.hasBody() );
        assertTrue( returnValue.getBody() instanceof UserInformationDto );

        final UserInformationDto body = (UserInformationDto) returnValue.getBody();
        assertEquals(dto, body.getUser());

        UserConstants.validateUserReturn(body);
    }


    @Test()
    public void withRecordedUser_OnRecord_ShouldThrowUserRecordedPreviouslyException() throws UserRecordedPreviouslyException {
        exceptionTest(new UserRecordedPreviouslyException(), HttpStatus.CONFLICT, "E-mail já existente" );
    }

    @Test()
    public void withRecordedUser_OnRecord_ShouldThrowUnexpectedException() throws UserRecordedPreviouslyException {
        exceptionTest(new RuntimeException(), HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inexperado" );
    }


    private void exceptionTest(final Throwable exception, final HttpStatus desiredStatus, final String desiredErrorMessage) throws UserRecordedPreviouslyException {
        doThrow(exception)
                .when(service)
                .register(ArgumentMatchers.eq(dto));

        ResponseEntity<?> returnValue = api.register(dto);

        assertEquals(desiredStatus, returnValue.getStatusCode());
        assertTrue(returnValue.hasBody());
        final ErrorMessageDto body = (ErrorMessageDto) returnValue.getBody();
        assertEquals(desiredErrorMessage, body.getMessage());
    }

}
