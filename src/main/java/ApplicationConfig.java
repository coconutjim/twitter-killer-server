import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.pmsoft.twitterkiller.dataaccess.DbSessionRepository;
import ru.pmsoft.twitterkiller.dataaccess.DbUserRepository;
import ru.pmsoft.twitterkiller.domain.factory.SessionFactory;
import ru.pmsoft.twitterkiller.domain.factory.UserFactory;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.util.RandomSaltGenerator;
import ru.pmsoft.twitterkiller.domain.util.SaltGenerator;
import ru.pmsoft.twitterkiller.domain.util.TokenGenerator;
import ru.pmsoft.twitterkiller.domain.util.UUIDGenerator;

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
                bind(UUIDGenerator.class).to(TokenGenerator.class).in(Singleton.class);
                bind(RandomSaltGenerator.class).to(SaltGenerator.class).in(Singleton.class);
                bind(UserFactory.class).in(Singleton.class);
                bind(SessionFactory.class).in(Singleton.class);
            }
        });
        packages(true, "ru.pmsoft.twitterkiller");
    }
}
