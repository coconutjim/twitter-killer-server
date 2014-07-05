import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Андрей on 05.07.2014.
 */
@ApplicationPath("webapi")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {

            }
        });
        packages(true, "ru.pmsoft.twitterkiller");

    }
}
