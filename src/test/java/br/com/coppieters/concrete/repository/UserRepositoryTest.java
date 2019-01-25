package br.com.coppieters.concrete.repository;

import br.com.coppieters.concrete.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static br.com.coppieters.concrete.UserConstants.USER_EMAIL;
import static br.com.coppieters.concrete.UserConstants.USER_NAME;
import static br.com.coppieters.concrete.UserConstants.produceUser;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    private static boolean databaseMocked = false;

    @Autowired
    private UserRepository repository;
    private User mockedUser = produceUser();

    @Before
    public void mockDatabase(){
        if( !databaseMocked ) {
            repository.save(mockedUser);
            databaseMocked=true;
        }
    }

    @Test
    public void withEmailFromRecordedUser_WhenFindByEmail_FoundUser(){
        Optional<User> possibleUser = repository.findByEmail(this.mockedUser.getEmail());

        assertTrue(possibleUser.isPresent());

        final User user = possibleUser.get();
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(USER_NAME, user.getName());
    }

}
