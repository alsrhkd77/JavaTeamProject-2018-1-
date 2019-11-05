package deu.java.team01.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private SocketChannel socketChannel;
    private List<SecondFrame> secondFrameList;
    private List<ThirdFrame> thirdFrameList;
    private int secondFrameListChecker;
    private int thirdFrameListChecker;

    public void startClient() {

        MainFrame mf = new MainFrame(this);
        secondFrameList = new LinkedList<>();
        thirdFrameList = new LinkedList<>();
        secondFrameListChecker = -1;
        thirdFrameListChecker = -1;
        ReadIPText readIPText = new ReadIPText();
        readIPText.scan();
        Thread thread = new Thread(() -> {
            try {

                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(true);

                logger.info("연결할 IP {} port {}", readIPText.getIp(), readIPText.getPort());
                socketChannel.connect(new InetSocketAddress(readIPText.getIp(), readIPText.getPort()));
                logger.info("[ 연결 완료 : {} ]", socketChannel.getRemoteAddress());
                mf.run();
            } catch (IOException e) {
                logger.error("[서버 통신 안 됨]");
                mf.unableToConnect(readIPText.getIp(), String.valueOf(readIPText.getPort()));
                stopClient();

                return;
            }
            receive();
        });
        thread.start();
    }

    public void stopClient() {
        try {
            logger.info("연결 끊음");
            if (socketChannel != null && socketChannel.isOpen()) {
                logger.warn("클라이언트를 종료한다~~~~");
                socketChannel.close();
                logger.info("잘 종료됐다!");
                System.exit(0);
            }
            else {
                logger.error("서버에 연결에 실패하여 강제 종료");
                System.exit(0);
            }
        } catch (IOException e) {
            logger.warn("클라이언트를 멈추는 중 IOException이 발생했다!");
            System.exit(-1);
        }

    }

    public void receive() {
        while (!Thread.interrupted()) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(2000);
                int readByteCount = socketChannel.read(byteBuffer);
                if (readByteCount == -1) {
                    throw new IOException();
                }
                byteBuffer.flip();
                Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                String data = charset.decode(byteBuffer).toString();
                logger.info("클라이언트 받기 완료 : {}", data);

                if (data.contains("클라이언트 종료")) {
                    stopClient();
                    break;
                }
                if (data.contains("공연 리스트")) {
                    if (data.contains("처음")) {
                        secondFrameListChecker++;
                        secondFrameList.add(new SecondFrame(this, secondFrameListChecker));
                        for (SecondFrame secondFrame : secondFrameList) {
                            if (secondFrame.getIndex() == secondFrameListChecker) {
                                secondFrame.clear();
                                secondFrame.run(data);
                                break;
                            }
                        }
                    } else {
                        String[] findIndex = data.split("\\n+");
                        String stringIndex = findIndex[findIndex.length - 1];
                        for (SecondFrame secondFrame : secondFrameList) {
                            if (secondFrame.getIndex() == Integer.parseInt(stringIndex)) {
                                secondFrame.clear();
                                secondFrame.run(data);
                                break;
                            }
                        }
                    }
                }
                if (data.contains("상세정보")) {
                    thirdFrameListChecker++;
                    thirdFrameList.add(new ThirdFrame(this, thirdFrameListChecker));
                    for (ThirdFrame thirdFrame : thirdFrameList) {
                        if (thirdFrame.getIndex() == thirdFrameListChecker) {
                            thirdFrame.clear();
                            thirdFrame.run(data);
                            break;
                        }
                    }
                }
                if (data.contains("날씨")) {
                    for (ThirdFrame thirdFrame : thirdFrameList) {
                        if (Integer.parseInt(data.split("\\n+")[data.split("\\n+").length - 1].split(":")[1].trim()) == thirdFrame.getIndex()) {
                            thirdFrame.weatherRequest(data);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("서버 통신 안 됨");
                stopClient();
                break;
            }
        }
    }

    public void secondFrameDelete(int index) {

        if (!(secondFrameList.isEmpty())) {
            logger.info("잘 삭제되나 보자~~ 원래 두번째 프레임 리스트의 크기는 {}이다", secondFrameList.size());
            for (SecondFrame secondFrame : secondFrameList) {

                if (secondFrame.getIndex() == index) {
                    secondFrameList.remove(secondFrame);
                    break;
                }

            }
            logger.info("삭제 후 두번째 프레임 리스트의 크기는 {}이다", secondFrameList.size());
        }


    }

    public void thirdFrameDelete(int index) {

        if (!(thirdFrameList.isEmpty())) {
            logger.info("잘 삭제되나 보자~~ 원래 세번째 리스트의 크기는 {}이다", thirdFrameList.size());
            for (ThirdFrame thirdFrame : thirdFrameList) {

                if (thirdFrame.getIndex() == index) {
                    thirdFrameList.remove(thirdFrame);
                    break;
                }

            }
            logger.info("삭제 후 세번째 리스트의 크기는 {}이다", thirdFrameList.size());
        }


    }

    public void send(String data) {
        Thread thread = new Thread(() -> {
            try {
                Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                ByteBuffer byteBuffer = charset.encode(data);
                socketChannel.write(byteBuffer);
                logger.info("데이터 보내기 완료 : {}", data);
            } catch (IOException e) {
                logger.error("서버 통신 안 됨");
                stopClient();
            }
        });
        thread.start();

    }
}
