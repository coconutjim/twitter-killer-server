//package ru.pmsoft.twitterkiller.models;
//
//import org.junit.Test;
//import ru.pmsoft.twitterkiller.domain.entity.User;
//import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
////import rest.Login;
//
///**
// * Created by Виктор on 04.07.2014.
// */
//public class UserRepositoryTest {
//
//    @Test
//    public void saveInUserRepositoryVerifyTest() {
//        UserRepository sut = mock(UserRepository.class);
//        String dummyName = "Vasiliy";
//        String dummyPassword = "12345678";
//        User user = new User(dummyName, dummyPassword);
//        sut.create(user);
//        verify(sut).create(user);
//    }
//
//    @Test
//    public void getByLoginInUserRepositoryVerifyTest() {
//        UserRepository sut = mock(UserRepository.class);
//        String dummyName = "Vasiliy";
//        sut.getByLogin(dummyName);
//        verify(sut).getByLogin(dummyName);
//    }
//}
