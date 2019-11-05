package deu.java.team01.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ClientMain {

    private static final Logger logger = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        logger.info("Client Start");
        Client client = new Client();
        client.startClient();

    }

}
