import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.pmsoft.twitterkiller.domain.dataaccess.DBTwittRepository;
import ru.pmsoft.twitterkiller.domain.dataaccess.DbUserRepository;
import ru.pmsoft.twitterkiller.domain.repository.TwittRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("webapi")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DbUserRepository.class).to(UserRepository.class).in(Singleton.class);
                bind(DBTwittRepository.class).to(TwittRepository.class).in(Singleton.class);
            }
        });
        packages(true, "ru.pmsoft.twitterkiller");

    }
}
