# jersey-kryo

Demo application uses Jersey/JAX-RS Message Body Writer and Reader using [Kryo serialization framework](https://github.com/EsotericSoftware/kryo)

## Try it

### Build it

Build the application:

```shell
mvn clean install
```

### Grizzly it

Maven packaging of this demo application is `war`. But it is possible to run the application using Grizzly HTTP container:

```shell
mvn exec:java
```

You can enable `FINEST` logging using `logging.properties`:

```shell
mvn exec:java -Djava.util.logging.config.file=target/test-classes/logging.properties
```

### Jetty it

Or you can run it as common webapp using Jetty container:

```shell
mvn jetty:run-war
```

Again, you can enable FINEST logging using logging.properties:

```shell
mvn jetty:run-war -Djava.util.logging.config.file=target/test-classes/logging.properties
```

### Check it

GET method is available at URL http://localhost:8080/, check it using `curl`:

```shell
curl -v http://localhost:8080/
```

Response should look like:

```
> GET / HTTP/1.1
> User-Agent: curl/7.40.0
> Host: localhost:8080
> Accept: */*
>
< HTTP/1.1 200 OK
< Content-Length: 20
< Content-Type: application/x-kryo
< Server: Jetty(8.1.16.v20140903)
<
Salzbur�*Wolfgan�
```

## Blog post

The Jersey Kryo support started as a "friday project" and now is part of Jersey project, currently as incubator module.

Evolution of the Kryo support shows list of blog posts:

1. [JAX-RS Kryo support introduced](http://yatel.kramolis.cz/2015/03/jax-rs-message-body-writer-using-kryo.html)
2. [Performance report of JAX-RS Kryo support](http://yatel.kramolis.cz/2015/04/jax-rs-kryo-message-body-writer-reader.html)
3. [JAX-RS Kryo support is part of Jersey project](http://yatel.kramolis.cz/2015/04/jaxrs-kryo-mb-provider-jersey-incubator.html)
