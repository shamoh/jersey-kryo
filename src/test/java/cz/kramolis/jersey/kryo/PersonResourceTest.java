package cz.kramolis.jersey.kryo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new JaxRsApplication();
    }

    @Test
    public void testGet() {
        final Person getResponse = target().request().get(Person.class);
        assertEquals("Wolfgang", getResponse.name);
        assertEquals(21, getResponse.age);
        assertEquals("Salzburg", getResponse.address);
    }

    @Test
    public void testPost() {
        final Person[] testData = new Person[] {new Person("Joseph", 23, "Nazareth"), new Person("Mary", 18, "Nazareth")};
        for (Person original : testData) {
            final Person postResponse = target().request()
                    .post(Entity.entity(original, "application/x-kryo"), Person.class);
            assertEquals(original, postResponse);
        }
    }

    @Test
    public void testPut() {
        final Response putResponse = target().request().put(Entity.entity(new Person("Jules", 12, "Paris"),
                "application/x-kryo"));
        assertEquals(204, putResponse.getStatus());
    }

}
