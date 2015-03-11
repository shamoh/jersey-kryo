package cz.kramolis.jersey.kryo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Consumes("application/x-kryo")
@Produces("application/x-kryo")
public class PersonResource {

    @POST
    public Person echo(final Person person) {
        return person;
    }

    @PUT
    public void put(final Person person) {
    }

    @GET
    public Person get() {
        return new Person("Wolfgang", 21, "Salzburg");
    }

}
