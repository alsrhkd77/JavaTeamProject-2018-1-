package deu.java.team01.server.dustozen;

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

import static deu.java.team01.server.ServiceKey.SERVICEKEY;

public class DustOzen {

    /**
     * @author 강선모
     * @update 2018-11-15
     */

    private String dust; // 미세먼지 받아오는 변수
    private String place; // 지역을 Performance API에서 들고오는 변수
    private String date; // 날짜를 Performance API에서 들고오는 변수
    private String result;
    private String urlAdr;
    private String readResult;
    private int calDate; // 날짜차이를 들고오는 변수
    private int count;
    private static final Logger logger = LoggerFactory.getLogger(DustOzen.class);

    public DustOzen(String date, String place, int calDate) {
        logger.info("미세먼지 클래스 객체가 생성되었다~");
        if (date.matches("^20[0-9]{2}-[0-1][0-9]-[0-3][0-9]$") && place.matches("^[\uac00-\ud7a3]{2,7}$") && calDate <= 1 && calDate >= 0) {
            logger.info("정확한 값이 들어옴");
            dust = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth";
            urlAdr = "";
            result = "";
            readResult = "";
            this.place = place;
            this.date = date;
            this.calDate = calDate;
        } else {
            logger.warn("잘못된 값이 들어옴");
            logger.info("날짜에 {} 장소에 {} 날짜 차이에 {}가 들어왔음",date,place,calDate);
            dust = null;
            urlAdr = null;
            result = "잘못된 요청";
            readResult = null;
            this.place = null;
            this.date = null;
            this.calDate = -1;
        }


    }

    public void setUrl() {
        if (dust != null) {
            StringBuilder urlBuilder = new StringBuilder(dust);
            urlBuilder.append("?ServiceKey=");
            urlBuilder.append(SERVICEKEY);
            urlBuilder.append("&numOfRows=1");
            urlBuilder.append("&pageSize=1");
            urlBuilder.append("&pageNo=1");
            urlBuilder.append("&startPage=1");
            urlBuilder.append("&searchDate=");
            urlBuilder.append(date);
            urlBuilder.append("&InformCode=PM10");
            this.urlAdr = urlBuilder.toString();
            logger.info("미세먼지 클래스가 만든 URL은 {}", urlAdr);
        }
    }

    public void setReadResult() {
        if(dust!=null) {
            try {
                URL url = new URL(urlAdr);
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
                readResult = "";
                String line = "";
                while ((line = br.readLine()) != null) {
                    readResult = readResult + line.trim();
                }
            } catch (MalformedURLException e) {
                logger.error("잘못된 URL : {}", urlAdr);
            } catch (UnsupportedEncodingException e) {
                logger.error("잘못된 문자열 인코딩");
                logger.error("현재 미세먼지의 urlAdr : {} readResult : {}", urlAdr, readResult);
            } catch (IOException e) {
                logger.error("IOExcention이 발생");
            }
        }

    }

    public void getTotalCount() {
        if(dust!=null) {
            setUrl();
            setReadResult();
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder;

                InputSource is = new InputSource(new StringReader(readResult));
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("//body/totalCount");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                Node node = nodeList.item(0);
                if (node.getNodeName().equals("totalCount")) {
                    count = Integer.parseInt(node.getTextContent());
                }
            } catch (XPathExpressionException e) {
                logger.error("XPath표현식이 잘못됐음");
            } catch (ParserConfigurationException e) {
                logger.error("파싱 설정 오류 발생");
                logger.error("현재 readResult : {}", readResult);
            } catch (SAXException e) {
                logger.error("SAXException이 발생");
            } catch (IOException e) {
                logger.error("IOException이 발생");
            }
        }
    }

    public void run() {
        if(dust!=null) {
            setUrl();
            setReadResult();
            StringBuilder resultBuilding = new StringBuilder("");

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder;

                InputSource is = new InputSource(new StringReader(readResult));
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("//items/item");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList child = nodeList.item(i).getChildNodes();
                    for (int j = 0; j < child.getLength(); j++) {
                        Node node = child.item(j);
                        if (calDate == 0 && i == 0) {
                            if (node.getNodeName().equals("informGrade")) {
                                String part = node.getTextContent();
                                String[] parts = part.split(",");
                                for (int k = 0; k < parts.length; k++) {//
                                    if (parts[k].contains(place)) {
                                        resultBuilding.append(parts[k]);
                                        break;
                                    }
                                }
                            }
                        } else if (calDate == 1 && i == (count - 1)) {
                            if (node.getNodeName().equals("informGrade")) {
                                String part = node.getTextContent();
                                String parts[] = part.split(",");
                                for (int k = 0; k < parts.length; k++) {//
                                    if (parts[k].contains(place)) {
                                        resultBuilding.append(parts[k]);
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
            } catch (IOException e) {
                logger.error("IOException이 발생");
            } catch (XPathExpressionException e) {
                logger.error("XPath표현식이 잘못됐음");
            } catch (ParserConfigurationException e) {
                logger.error("파싱 설정 오류 발생");
                logger.error("현재 readResult : {}", readResult);
            } catch (SAXException e) {
                logger.error("SAXException이 발생");
            }
            result = resultBuilding.toString();
        }

    }

    public String getResult() {
        logger.info("미세먼지 파싱 결과 : {}", result);
        return result;
    }
}