package client;

import exceptions.ResponseException;
import model.AuthData;
import model.Login;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws MalformedURLException, URISyntaxException {

        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");

    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }



}
