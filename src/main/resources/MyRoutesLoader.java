import org.apache.camel.builder.RouteBuilder;

public class MyRoutesLoader extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:foo2?period=5000")
        .setBody(constant("Hello My XML World"))
        .log("Body ${body}");
    }
    
}
