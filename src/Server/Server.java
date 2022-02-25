package Server;

public class Server {

    public static void main(String[] args) {

        MessageAnalyzer analyzer = MessageAnalyzer.getInstance();
        TCPServer server = new TCPServer();
        server.setReceiver(analyzer);
        server.run();

    }

}
