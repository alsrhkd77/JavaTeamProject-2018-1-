package deu.java.team01.server.weather;

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

import static deu.java.team01.server.ServiceKey.SERVICEKEY;

public class TodayWeatherClass extends AbstractWeather {
    private StringBuilder resultBuilding;
    private static final Logger logger = LoggerFactory.getLogger(TodayWeatherClass.class);

    public TodayWeatherClass(String regID, int dateDays) {
        logger.info("오늘~2일 날씨 클래스 객체가 생성됨");
        if (regID.matches("^[0-9]{2}[A-Z][0-9]{5}$") && dateDays < 3) {
            logger.info("정확한 값이 들어옴");
            headURL = "http://newsky2.kma.go.kr/service/VilageFrcstDspthDocInfoService/WidOverlandForecast";
            readResult = new StringBuilder("");
            result = "";
            this.regID = regID;
            this.dateDays = dateDays;
        }else{
            logger.warn("잘못된 값이 들어옴");
            logger.info("지역코드에 {} 날짜 차이에 {}가 들어왔음",regID,dateDays);
            this.regID = null;
            headURL = null;
            readResult=null;
            result = "잘못된 요청";

        }
    }//생성자

    @Override
    protected void setUrl() {
        if(regID!=null) {
            StringBuilder urlBuilder = new StringBuilder(headURL);
            urlBuilder.append("?ServiceKey=");
            urlBuilder.append(SERVICEKEY);
            urlBuilder.append("&regId=");
            urlBuilder.append(regID);
            urlBuilder.append("&numOfRows=10&pageSize=10&pageNo=1&startPage=1");
            urlAdr = urlBuilder.toString();
            logger.info("오늘~2일 날씨 클래스가 만든 URL은 : {}", urlAdr);
        }
    }

    public void append() {
        if(regID!=null) {
            setUrl();
            setReadResult();
            resultBuilding = new StringBuilder("\n");
            System.out.println(urlAdr);
            try {


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                InputSource is = new InputSource(new StringReader(readResult.toString()));
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("//response//body//items//item");
                String str;
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList child = nodeList.item(i).getChildNodes();
                    DateCheck ch = new DateCheck(nodeList.getLength(), 2);
                    if (nodeList.getLength() == 5 || nodeList.getLength() == 6) {
                        for (int j = 0; j < child.getLength(); j++) {
                            Node node = child.item(j);
                            if (node.getNodeName().equals("numEf")) {
                                str = node.getTextContent();
                                if (str.equals(ch.getAm())) {
                                    if (ch.getAm().equals("-1"))
                                        break;
                                    resultBuilding.append("오전\n");
                                    GetInfomation(child);
                                }
                                if (str.equals(ch.getPm())) {
                                    resultBuilding.append("오후\n");
                                    GetInfomation(child);
                                }
                            }

                        }
                    }
                }
                result = resultBuilding.toString();
            } catch (ParserConfigurationException e) {
                logger.error("파싱 설정 오류!!");
                logger.error("현재 readResult : {}", readResult);
                logger.error("현재 URL {}", urlAdr);
            } catch (IOException e) {
                logger.error("IOException 발생");
            } catch (XPathExpressionException e) {
                logger.error("Xpath 표현식 오류");
            } catch (SAXException e) {
                logger.error("SAX오류");
            }
        }

    }

    private void GetInfomation(NodeList child) {
        if(regID!=null) {
            String weather;
            for (int j = 0; j < child.getLength(); j++) {
                Node node = child.item(j);
                if (node.getNodeName().equals("wf")) {
                    resultBuilding.append("날씨 :" + node.getTextContent() + "\n");
                }
                if (node.getNodeName().equals("rnSt")) {
                    resultBuilding.append("강수 확률 : " + node.getTextContent() + "\n");
                }
                if (node.getNodeName().equals("rnYn")) {
                    weather = weatherType(node.getTextContent());
                    resultBuilding.append("강수 형태 : " + weather + "\n");
                }

                if (node.getNodeName().equals("ta")) {
                    resultBuilding.append("예상 기온 : " + node.getTextContent() + "\n");
                }
            }
        }

    }

    private String weatherType(String type) {

        String str = " ";
        if (type.equals("0")) str = "강수없음";
        else if (type.equals("1")) str = "비";
        else if (type.equals("2")) str = "비/눈";
        else if (type.equals("3")) str = "눈";

        return str;
    }

}
	    
