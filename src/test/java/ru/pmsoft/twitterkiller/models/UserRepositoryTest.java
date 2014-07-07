package ru.pmsoft.twitterkiller.models;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import org.junit.Test;
import rest.Login;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
* Created by Виктор on 04.07.2014.
*/
public class UserRepositoryTest {

    @Test
    public void saveInUserRepositoryVerifyTest(){
        UserRepository sut = mock(UserRepository.class);
        String dummyName = "Vasiliy";
        String dummyPassword = "12345678";
        User user = new User(dummyName,dummyPassword);
        sut.save(user);
        verify(sut).save(user);
    }

    @Test
    public void getByLoginInUserRepositoryVerifyTest(){
        UserRepository sut = mock(UserRepository.class);
        String dummyName = "Vasiliy";
        sut.getByLogin(dummyName);
        verify(sut).getByLogin(dummyName);
    }



}
