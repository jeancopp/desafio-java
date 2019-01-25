package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.UserRecordedPreviouslyException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.UserRegisterService;
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

            UserInformationDto returnValue = UserInformationDto.builder()
                    .user(dto)
                    .created(user.getCreated())
                    .lastLogin(user.getLastLogin())
                    .modified(user.getModified())
                    .id(user.getId())
                    .token(user.getToken())
                    .build();

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
    public UserRegisterApi(UserRegisterService service) {
        this.service = service;
    }

    private UserRegisterService service;
}
