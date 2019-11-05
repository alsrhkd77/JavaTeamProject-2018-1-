package deu.java.team01.server;

import deu.java.team01.server.bus.BusStationByGPS;
import deu.java.team01.server.performance.RequestInfo;
import deu.java.team01.server.performance.RequestSearchGenre;
import deu.java.team01.server.weather.WeatherClassMainPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private ExecutorService executorService;
    private ServerSocketChannel serverSocketChannel;
    private List<Client> connections = new LinkedList<>();
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private class Client {
        SocketChannel socketChannel;

        private Client(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
            receive();
        }

        private void receive() {
            Runnable runnable = () -> {
                while (!Thread.interrupted()) {
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(2000);
                        int readByteCount = socketChannel.read(byteBuffer);
                        if (readByteCount == -1) {
                            throw new IOException();
                        }

                        logger.info("[ 요청 처리 {} : {} ]", socketChannel.getRemoteAddress(), Thread.currentThread().getName());
                        byteBuffer.flip();
                        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                        String data = charset.decode(byteBuffer).toString();
                        logger.info(data);

                        if (data.contains("서버 종료")) {
                            stopServer();
                            break;
                        }
                        if (data.contains("공연 리스트")) {
                            int fPage = 0;
                            String fromDate;
                            String toDate;
                            String sido;
                            String gugun;
                            String genre;
                            String[] parts = data.split("\\n+");
                            if (parts.length == 8) {

                                if (parts[0].equals("처음요청")) {
                                    fPage = 1;
                                } else {
                                    fPage = Integer.parseInt(parts[0]);
                                }
                                fromDate = parts[1];
                                if (parts[2].equals("전체")) {
                                    toDate = "";
                                } else {
                                    toDate = parts[2];
                                }
                                if (parts[3].equals("전체")) {
                                    sido = "";
                                } else {
                                    sido = parts[3];
                                }
                                if (parts[4].equals("-시/도를 선택하세요-") || parts[4].equals("전체") || parts[4].equals("----")) {
                                    gugun = "";
                                } else {
                                    gugun = parts[4];
                                }
                                if (parts[5].equals("전체")) {
                                    genre = "";
                                } else {
                                    genre = parts[5];
                                }
                                StringBuilder resultBuilder = new StringBuilder("");
                                RequestSearchGenre requestSearchGenre = new RequestSearchGenre(fromDate, toDate, sido, gugun, genre, fPage);
                                for (int i = 0; i < requestSearchGenre.getResultBuilder().length; i++) {
                                    resultBuilder.append(requestSearchGenre.getResultBuilder()[i]);
                                }
                                resultBuilder.append(requestSearchGenre.getTotalPage());
                                resultBuilder.append("\n");
                                resultBuilder.append(requestSearchGenre.getTotalCount());
                                resultBuilder.append("\n");
                                resultBuilder.append(fPage);
                                resultBuilder.append("\n");
                                resultBuilder.append(data);
                                this.send(resultBuilder.toString());


                            }

                        }
                        if (data.contains("세번째페이지")) {
                            StringBuilder resultBuilder = new StringBuilder("상세정보\n");
                            String[] number = data.split(":");
                            String performnumber = number[1];
                            RequestInfo requestInfo = new RequestInfo(performnumber);
                            String performInfoResult = requestInfo.getResult();
                            resultBuilder.append(performInfoResult);
                            resultBuilder.append(requestInfo.getMapsUrlFromGPS());
                            BusStationByGPS busStationByGPS = new BusStationByGPS(requestInfo.getGps()[0], requestInfo.getGps()[1]);
                            busStationByGPS.run();
                            resultBuilder.append("\n");
                            resultBuilder.append(busStationByGPS.getResult());
                            resultBuilder.append("\n처음요청\n");
                            this.send(resultBuilder.toString());


                        }
                        if (data.contains("날씨")) {
                            String temp1 = data.split("\\n+")[2];
                            String temp = temp1.split(":")[1].trim();
                            WeatherClassMainPoint weatherClassMainPoint = new WeatherClassMainPoint(temp);
                            weatherClassMainPoint.run();
                            String result = weatherClassMainPoint.getResult();
                            result = result + "\n" + data.split("\\n+")[data.split("\\n+").length - 3];
                            this.send(result);
                        }

                    } catch (IOException e) {
                        try {
                            socketChannel.close();
                            logger.error("클라이언트 통신이 안 됨 : {} : {} ", socketChannel.getRemoteAddress(), Thread.currentThread().getName());

                        } catch (IOException e1) {
                            connections.remove(this);
                            logger.error("닫는 중 IOException이 발생");
                            Thread.currentThread().interrupt();

                        }
                    }
                }
            };

            executorService.submit(runnable);
        }

        private void send(String data) {
            Runnable runnable = () -> {
                try {
                    Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                    ByteBuffer byteBuffer = charset.encode(data);
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    try {
                        logger.error("[ 클라이언트 통신 안 됨 : {} : {} ]", socketChannel.getRemoteAddress(), Thread.currentThread().getName());
                        connections.remove(Client.this);
                        if (socketChannel.isConnected())
                            socketChannel.close();
                    } catch (IOException e1) {
                        logger.error("IOException 발생");
                    }
                }
            };
            executorService.submit(runnable);
        }

    }

    public void startServer() {

        try {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//cpu코어 수에 맞게 스레드를 생성해서 관리한다.
            logger.info("만들 스레드의 갯수는 {}개다",Runtime.getRuntime().availableProcessors());
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(true);
            serverSocketChannel.bind(new InetSocketAddress(5001));
        } catch (IOException e) {
            logger.error("예외가 발생하여 서버를 강제 종료한다.");
            if (serverSocketChannel.isOpen()) {
                stopServer();
                return;
            }
        }
        Runnable runnable = () -> {
            while (true) {


                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    logger.info("[연결 수락 :  {} : {} ]", socketChannel.getRemoteAddress(), Thread.currentThread().getName());
                    Client client = new Client(socketChannel);
                    connections.add(client);
                    logger.info("[ 연결 개수: {} ]", connections.size());

                    if (connections.size() > Runtime.getRuntime().availableProcessors()) {
                        logger.error("너무 많은 연결");
                        stopServer();
                        break;
                    }

                } catch (IOException e) {
                    logger.error("오류가 발생하여 강제 종료한다.");
                    if (serverSocketChannel.isOpen()) {
                        stopServer();
                    }
                    break;
                }
            }


        };
        executorService.submit(runnable);//스레드풀에서 처리한다


    }

    private void stopServer() {
        try {
            Iterator<Client> iterator = connections.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.socketChannel.close();
                iterator.remove();
            }
            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                serverSocketChannel.close();
            }
            if (executorService != null && executorService.isShutdown()) {
                executorService.shutdown();
            }
            logger.warn("서버 멈춤");
            System.exit(-1);
        } catch (IOException e) {
            logger.warn("서버 멈추는 도중 IOException 발생");
            System.exit(-2);
        }
    }
}
