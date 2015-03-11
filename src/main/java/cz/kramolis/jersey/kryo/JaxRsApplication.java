package cz.kramolis.jersey.kryo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class JaxRsApplication extends Application {

    static final Set<Class<?>> APP_CLASSES = new HashSet<Class<?>>() {{
        add(KryoMessageBodyProvider.class);
        add(PersonResource.class);
    }};

    @Override
    public Set<Class<?>> getClasses() {
        return APP_CLASSES;
    }

}
