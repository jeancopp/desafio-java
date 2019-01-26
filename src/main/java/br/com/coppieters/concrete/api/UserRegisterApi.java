package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.UserRegisterService;
import br.com.coppieters.concrete.service.maker.UserInformationMaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class UserRegisterApi {

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody() @Valid UserDto dto) {
        try {
            final User user = service.register(dto);
            UserInformationDto returnValue =informationMaker.make(user);

            return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
        }catch(UserRecordedPreviouslyException e){
            log.info("User is in the base", e);
            final ErrorMessageDto message = new ErrorMessageDto("E-mail já existente");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }catch (Exception e){
            log.info("Unexpected error",e);
            final ErrorMessageDto message = new ErrorMessageDto("Erro interno inexperado");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    public UserRegisterApi(UserRegisterService service, UserInformationMaker informationMaker) {
        this.service = service;
        this.informationMaker = informationMaker;
    }

    private final UserRegisterService service;
    private final UserInformationMaker informationMaker;
}
