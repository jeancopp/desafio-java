package br.com.coppieters.concrete.api;

import br.com.coppieters.concrete.domain.dto.ErrorMessageDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.exception.ExpiredSessionException;
import br.com.coppieters.concrete.domain.exception.InvalidUserDataException;
import br.com.coppieters.concrete.domain.exception.UnauthorizedTokenException;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.service.UserProfileService;
import br.com.coppieters.concrete.service.maker.UserInformationMaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileApi {

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") long userId, @RequestHeader("Authorization") String token) {
        try {
            service.verify(token);
            service.validate(userId, token);

            final User user = service.getUserInformation(userId, token);
            UserInformationDto response = informationMaker.make(user);

            return new ResponseEntity<>( response, HttpStatus.OK );
        }catch (ExpiredSessionException|InvalidUserDataException| UnauthorizedTokenException e){
            final ErrorMessageDto message = new ErrorMessageDto(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            final ErrorMessageDto message = new ErrorMessageDto("Erro interno inexperado");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    public UserProfileApi(UserProfileService service, UserInformationMaker informationMaker) {
        this.service = service;
        this.informationMaker = informationMaker;
    }

    private final UserProfileService service;
    private final UserInformationMaker informationMaker;
}
