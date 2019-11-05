package deu.java.team01.server.bus;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import static deu.java.team01.server.ServiceKey.SERVICEKEY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusStationByGPS {
    private String result;
    private String gpsX;
    private String gpsY;
    private String busUrl;
    private String urlAdr;
    private String readResult;
    private int checker;
    private static final Logger logger = LoggerFactory.getLogger(BusStationByGPS.class);

    public BusStationByGPS(String gpsX, String gpsY) {
        logger.info("BusStationByGPS 시작");
        if (gpsX.matches("^[0-9]{0,3}.[0-9]+$") && gpsY.matches("^[0-9]{0,3}.[0-9]+$")) {
            logger.info("정확한 값이 들어옴");
            this.gpsX = gpsX;
            this.gpsY = gpsY;
            readResult = "";
            result = "";
            checker = 0;
            busUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCrdntPrxmtSttnList?";
        } else {
            logger.warn("잘못된 값이 들어옴");
            logger.info("gpsX에 {} gpsY에 {}가 들어왔음",gpsX,gpsY);
            this.gpsX = null;
            this.gpsY = null;
            readResult = null;
            result = "잘못된 요청";
            checker = 0;
            busUrl = null;
        }


    }

    private void urlBuilding() {
        if (gpsX != null && gpsY != null) {
            StringBuilder urlBuilder = new StringBuilder(busUrl);
            urlBuilder.append("serviceKey=");
            urlBuilder.append(SERVICEKEY);
            urlBuilder.append("&gpsLati=");
            urlBuilder.append(gpsY);
            urlBuilder.append("&gpsLong=");
            urlBuilder.append(gpsX);

            urlAdr = urlBuilder.toString();
            logger.info("버스 GPS검색이 만든 URL은 {}", urlAdr);
        }
    }

    private void setReadResult() {
        if (gpsX != null && gpsY != null) {
            try {
                URL url = new URL(urlAdr);
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));

                String line;
                while ((line = br.readLine()) != null) {
                    readResult = readResult + line.trim();

                }
            } catch (MalformedURLException e) {
                logger.error("잘못된 URL : {}", urlAdr);
            } catch (UnsupportedEncodingException e) {
                logger.error("잘못된 문자열 인코딩");
                logger.error("현재 GPS기반 주변 버스의 urlAdr : {} readResult : {}", urlAdr, readResult);
            } catch (IOException e) {
                logger.error("IOExcention이 발생");
            }
        }
    }

    public void run() {
        urlBuilding();
        setReadResult();
        if (gpsY == null && gpsX == null) {
            logger.warn("잘못된 값이 들어왔으므로 아무 일도 하지 않는다");
            return;
        }
        StringBuilder resultBuilder = new StringBuilder("");
        logger.info("버스 GPS 검색 클래스가 파싱 함수에 들어감");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            InputSource is = new InputSource(new StringReader(readResult));
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(is);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);


                    if (node.getNodeName().contains("nodenm")) {
                        if (node.getTextContent().equals("")) {
                            resultBuilder.append("정류소 한글명 없음");
                        } else {
                            resultBuilder.append(node.getTextContent() + "-");
                            checker++;
                            if (checker > 2) {
                                break;
                            }
                        }

                    }


                }
                if (checker > 2) {
                    break;
                }


            }

        } catch (ParserConfigurationException e) {
            logger.error("파싱 설정 오류 발생");
            logger.error("현재 readResult : {}", readResult);
        } catch (IOException e) {
            logger.error("IOException이 발생");
        } catch (XPathExpressionException e) {
            logger.error("XPath표현식이 잘못됐음");
        } catch (SAXException e) {
            logger.error("SAXException이 발생");
        }

        result = resultBuilder.toString();
        if (resultBuilder.toString().equals("")) {
            result = "버스 정류소 없음";
        }
    }

    public String getResult() {
        logger.info("버스 GPS 클래스가 파싱한 결과물 : {}", result);
        return result;
    }

}
