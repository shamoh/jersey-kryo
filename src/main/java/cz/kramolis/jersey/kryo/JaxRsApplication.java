package cz.kramolis.jersey.kryo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;

public class JaxRsApplication extends Application {

    static final Set<Class<?>> APP_CLASSES = new HashSet<Class<?>>() {{
        add(PersonResource.class);
        add(LoggingFilter.class);
    }};

    @Override
    public Set<Class<?>> getClasses() {
        return APP_CLASSES;
    }

}
