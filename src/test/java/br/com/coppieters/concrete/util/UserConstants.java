package br.com.coppieters.concrete.util;

import br.com.coppieters.concrete.domain.dto.PhoneDto;
import br.com.coppieters.concrete.domain.dto.UserDto;
import br.com.coppieters.concrete.domain.dto.UserInformationDto;
import br.com.coppieters.concrete.domain.model.User;
import br.com.coppieters.concrete.domain.model.UserPhone;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UserConstants {

    public static final long ID = 1L;
    public static final String USER_NAME = "João da Silva";
    public static final String USER_EMAIL = "joao@silva.org";
    public static final String PASSWORD = "hunter2";
    public static final String CRIPT_PASSWORD = "2retnuh";
    public static final LocalDateTime MODIFIER_DATE = LocalDateTime.of(2019, Month.JANUARY, 01, 01,00,00);
    public static final LocalDateTime CREATED_DATE = LocalDateTime.of(2019, Month.JANUARY, 01, 01,00,00);
    public static final LocalDateTime LAST_LOGIN_DATE = LocalDateTime.of(2019, Month.JANUARY, 01, 01,00,00);;
    public static final String TOKEN = "TOKEN";

    public static User produceUser() {
        return User.builder()
                .id(ID)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(CRIPT_PASSWORD)
                .modified(MODIFIER_DATE)
                .created(CREATED_DATE)
                .lastLogin(LAST_LOGIN_DATE)
                .token(TOKEN)
                .phones(Arrays.asList(UserPhone.builder().ddd("21").number("987654321").build()))
                .build();
    }

    public static UserDto produceUserDto() {
        return UserDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(PASSWORD)
                .phones(Arrays.asList(new PhoneDto("987654321", "21")))
                .build();
    }

    public static void validateUserReturn(final  UserInformationDto user ) {
        assertEquals(LAST_LOGIN_DATE, user.getLastLogin());
        assertEquals(MODIFIER_DATE, user.getModified() );
        assertEquals(CREATED_DATE, user.getCreated() );
        assertEquals(TOKEN, user.getToken());
        assertEquals(ID, user.getId().longValue());
    }
}
