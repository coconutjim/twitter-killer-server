import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.pmsoft.twitterkiller.dataaccess.DbTweetRepository;
import ru.pmsoft.twitterkiller.dataaccess.DbSessionRepository;
import ru.pmsoft.twitterkiller.dataaccess.DbUserRepository;
import ru.pmsoft.twitterkiller.domain.factory.SessionFactory;
import ru.pmsoft.twitterkiller.domain.factory.UserFactory;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.util.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DbUserRepository.class).to(UserRepository.class).in(Singleton.class);
                bind(DbSessionRepository.class).to(SessionRepository.class).in(Singleton.class);
                bind(DbTweetRepository.class).to(TweetRepository.class).in(Singleton.class);
                bind(UUIDGenerator.class).to(StringGenerator.class).in(Singleton.class).named("token");
                bind(RandomSaltGenerator.class).to(StringGenerator.class).in(Singleton.class).named("salt");
                bind(SHA256PasswordEncrypter.class).to(PasswordEncrypter.class).in(Singleton.class);
                bind(UserFactory.class).to(UserFactory.class).in(Singleton.class);
                bind(SessionFactory.class).to(SessionFactory.class).in(Singleton.class);

            }
        });
        packages(true, "ru.pmsoft.twitterkiller");

    }
}
