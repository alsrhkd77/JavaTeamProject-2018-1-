package deu.java.team01.server.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class GetSttnAcctoArvlPrearngeInfoList {
    private String result;
    private String getSttnAcctoArvlPrearngeInfoListUrl;
    private String urlAdr;
    private StringBuilder resultBuilder;
    private StringBuilder readResult;
    private String cityCode;
    private String stationID;
    private static final Logger logger = LoggerFactory.getLogger(GetSttnAcctoArvlPrearngeInfoList.class);

    public GetSttnAcctoArvlPrearngeInfoList(String cityCode, String stationID) {
        logger.info("GetSttnAcctoArvlPrearngeInfoList 시작");
        this.cityCode = cityCode;
        this.stationID = stationID;
        getSttnAcctoArvlPrearngeInfoListUrl = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList";
        readResult = new StringBuilder("");
        result = "";
    }

    public void urlBuilding() {
        StringBuilder urlBuilder = new StringBuilder(getSttnAcctoArvlPrearngeInfoListUrl);
        urlBuilder.append("?serviceKey=");
        urlBuilder.append(SERVICEKEY);
        urlBuilder.append("&cityCode=");
        urlBuilder.append(cityCode);
        urlBuilder.append("&routeId=");
        urlBuilder.append(stationID);
        logger.info("버스역 상세정보 클래스가 만든 URL은 {}", urlAdr);
    }

    public void setReadResult() {
        try {
            URL url = new URL(urlAdr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));

            String line = "";
            while ((line = br.readLine()) != null) {
                readResult.append(line.trim());

            }
        } catch (MalformedURLException e) {
            logger.error("잘못된 URL : {}", urlAdr);
        } catch (UnsupportedEncodingException e) {
            logger.error("잘못된 문자열 인코딩");
            logger.error("현재 버스역 상세정보의 urlAdr : {} readResult : {}", urlAdr, readResult);
        } catch (IOException e) {
            logger.error("IOExcention이 발생");
        }

    }

    public void run() {
        urlBuilding();
        setReadResult();
        resultBuilder = new StringBuilder("");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            InputSource is = new InputSource(new StringReader(readResult.toString()));

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
    }

    public String getResult() {
        logger.info("버스역 상세정보 클래스가 파싱한 결과물 : {}", result);
        return result;
    }
}
