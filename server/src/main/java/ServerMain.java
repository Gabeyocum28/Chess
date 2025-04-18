import server.Server;

public class ServerMain {
    public static void main(String[] args) {
        int server = new Server().run(8080);
    }
}