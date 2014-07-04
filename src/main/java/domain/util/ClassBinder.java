package domain.util;

import domain.repository.TestRepository;
import domain.repository.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by Виктор on 04.07.2014.
 */
public class ClassBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserRepository.class).to(TestRepository.class);
    }
}
