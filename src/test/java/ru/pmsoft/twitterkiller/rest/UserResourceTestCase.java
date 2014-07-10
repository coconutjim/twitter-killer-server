package ru.pmsoft.twitterkiller.rest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.factory.UserFactory;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;
import ru.pmsoft.twitterkiller.rest.exceptions.ExceptionBody;

import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class UserResourceTestCase {

    private static UserResource createSystemUnderTest(UserRepository repository,
                                                      SessionRepository sessionRepository) {
        return new UserResource(repository==null ? mock(UserRepository.class) : repository,
                                sessionRepository == null ? mock(SessionRepository.class) : sessionRepository);
    }

    @Test(dataProvider = "invalidAuthenticationTestData")
    public void login_withInvalidArguments_shouldThrowClientException(
            String login, String password, ExceptionBody expected) throws GeneralSecurityException{
        UserResource sut = createSystemUnderTest(null, null);

        try {
            sut.login(login, password);
        } catch (ClientException ex) {
            assertEquals(ex.getResponse().getEntity(), expected);
            return;
        }
        fail();
    }

    @DataProvider
    public Object[][] invalidAuthenticationTestData() {
        return new Object[][]{
                new Object[]{null, "foo", new ExceptionBody("Login can not be empty")},
                new Object[]{"foo", null, new ExceptionBody("Password can not be empty")},
                new Object[]{"foo", "bar", new ExceptionBody("User is not found")}
        };
    }

    @Test
    public void login_ifPasswordIsNotCorrect_shouldThrowClientException() throws GeneralSecurityException {

        final String password = "bar";
        final String login = "foo";
        UserFactory userFactoryStub = new UserFactory();
        User user  = userFactoryStub.create(login, password);
        UserRepository repositoryStub = mock(UserRepository.class);
        when(repositoryStub.getByLogin(login)).thenReturn(user);
        UserResource sut = createSystemUnderTest(repositoryStub, null);

        try {
            sut.login("foo", "bzr");
        } catch (ClientException e) {
            assertEquals(e.getResponse().getEntity(), new ExceptionBody("Password is not correct"));
            return;
        }
        fail();
    }

    @Test(dataProvider = "invalidRegisterTestData")
    public void register_withInvalidArguments_shouldThrowClientException
            (String login, String password, ExceptionBody exceptionBody) throws GeneralSecurityException
    {
        UserResource sut = createSystemUnderTest(null, null);
        try
        {
            sut.register(login, password);
        }
        catch (ClientException ex) {
            assertEquals(ex.getResponse().getEntity(), exceptionBody);
            return;
        }
        fail();
    }

    @DataProvider
    public Object[][] invalidRegisterTestData()
    {
        return new Object[][]{
            new Object[]{null, "foo", new ExceptionBody("Login can not be empty")},
                new Object[]{"foo", null, new ExceptionBody("Password can not be empty")},
        };
    }

    @Test
    public void register_ifLoginIsAlreadyTaken_shouldThrowClientException()throws GeneralSecurityException
    {
        UserRepository userRepository = mock(UserRepository.class);
        UserResource sut = createSystemUnderTest(userRepository, null);
        String login = "foo";
        String password = "bar";
        User user = mock(User.class);
        user = new UserFactory().create(login, password);
        when(userRepository.getByLogin(login)).thenReturn(user);
        try
        {
            sut.register(login, password);
        }
        catch (ClientException ex){
            assertEquals(ex.getResponse().getEntity(),
                new ExceptionBody("Login is already taken"));
            return;
        }
        fail();
    }
    @Test
    public void register_thisTestWorksWell() throws GeneralSecurityException
    {
        UserResource sut = createSystemUnderTest(null,null);
        Response resp = sut.register("foo", "bar");
        String s = (String)resp.getEntity();
        String arr[] = s.split("\"");
        assertEquals(arr[3], "foo");
    }
}
