package domain.util;

import domain.repository.UserRepository;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Виктор on 04.07.2014.
 */
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig(){
        register(new ClassBinder());

    }
}
