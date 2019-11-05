package deu.java.team01.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//client 내에서만 쓰므로 package private 가시성을 사용
class ReadIPText {
    private String ip;
    private int port;
    private File iptxt;
    private static final Logger logger = LoggerFactory.getLogger(ReadIPText.class);

    ReadIPText() {
        ip = "localhost";
        port = 5001;
        iptxt = new File("./src//deu//java//team01//client//ip.txt");
    }

    void scan() {
        try (Scanner scanner = new Scanner(iptxt)) {


            if (scanner.hasNextLine()) {
                String inputString = scanner.nextLine();
                String[] splitInputString = inputString.split(":");
                if (splitInputString[0].matches("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z") || splitInputString[0].equals("localhost")) {
                    logger.info("정확한 ipv4 형식의 IP");
                    ip = splitInputString[0];
                } else {
                    logger.info("잘못된 형태의 ip {} default로 localhost를 사용", splitInputString[0]);
                }
                if (splitInputString[1].matches("\\d+")) {
                    port = Integer.parseInt(splitInputString[1]);
                } else {
                    logger.info("잘못된 형태의 port {} default로 5001번 포트를 사용", splitInputString[1]);
                }

            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            logger.info("ip.txt가 없거나 내용이 없음 기본(localhost:5001) 그대로 연결");
        }
    }

    int getPort() {
        return port;
    }

    String getIp() {
        return ip;
    }
}
