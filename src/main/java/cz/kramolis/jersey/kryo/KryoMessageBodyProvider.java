package cz.kramolis.jersey.kryo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

@Consumes("application/x-kryo")
@Produces("application/x-kryo")
public class KryoMessageBodyProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private final KryoPool kryoPool;

    public KryoMessageBodyProvider() {
        final KryoFactory kryoFactory = new KryoFactory() {
            public Kryo create() {
                final Kryo kryo = new Kryo();
                // configure kryo instance, customize settings
                return kryo;
            }
        };
        kryoPool = new KryoPool.Builder(kryoFactory).softReferences().build();
    }

    //
    // MessageBodyWriter
    //

    @Override
    public long getSize(final Object object, final Class<?> type, final Type genericType,
                        final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType,
                               final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(final Object object, final Class<?> type, final Type genericType,
                        final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream)
            throws IOException, WebApplicationException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final Output output = new Output(baos);
        kryoPool.run(new KryoCallback() {
            public Object execute(Kryo kryo) {
                kryo.writeObject(output, object);
                return null;
            }
        });
        output.close();

        entityStream.write(baos.toByteArray());
    }

    //
    // MessageBodyReader
    //

    @Override
    public boolean isReadable(final Class<?> type, final Type genericType,
                              final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(final Class<Object> type, final Type genericType,
                           final Annotation[] annotations, final MediaType mediaType,
                           final MultivaluedMap<String, String> httpHeaders,
                           final InputStream entityStream) throws IOException, WebApplicationException {
        try {
            final Input input = new Input(entityStream);
            final Object object = kryoPool.run(new KryoCallback() {
                public Object execute(Kryo kryo) {
                    return kryo.readObject(input, type);
                }
            });
            input.close();

            return object;
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

}
