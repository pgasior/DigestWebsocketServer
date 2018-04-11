package pl.gasior;

import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.SecurityFilter;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Config config = new HttpDigestConfigFactory("testUser", "testPassword").build();
        port(8090);
        staticFileLocation("/foo");

        webSocket("/ws", WsHandler.class);
        before("/ws", new SecurityFilter(config, "DirectDigestAuthClient"));
        get("/test", (request, response) -> "asdf");

        init();
    }

}
