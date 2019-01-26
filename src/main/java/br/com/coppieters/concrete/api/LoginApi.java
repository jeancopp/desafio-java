package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.LoginDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UserNotFoundException;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.LoginService;
import br.com.coppieters.concrete.service.maker.UserInformationMaker;
import br.com.coppieters.concrete.service.maker.UserMaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginApi {

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto)  {
        try {
            final User user = service.login(dto);
            UserInformationDto returnValue = maker.make(user);

            return new ResponseEntity<>(returnValue, HttpStatus.FOUND);
        }catch(UserNotFoundException|InvalidUserDataException e){
            final ErrorMessageDto message = new ErrorMessageDto("Usuário e/ou senha inválidos");
            HttpStatus status = e instanceof InvalidUserDataException ? HttpStatus.UNAUTHORIZED : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(message, status);
        }catch (Exception e){
            log.info("Unexpected error",e);
            final ErrorMessageDto message = new ErrorMessageDto("Erro interno inexperado");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    public LoginApi(LoginService service, UserInformationMaker maker ) {
        this.service = service;
        this.maker = maker;
    }

    private final LoginService service;
    private final UserInformationMaker maker;
}
