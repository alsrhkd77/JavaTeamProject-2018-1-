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

public class MiddleLandWeatherConfClass extends AbstractWeather {

    private static final Logger logger = LoggerFactory.getLogger(MiddleLandWeatherConfClass.class);

    /**
     * @param date     getDate()함수에서 만든 String형태의 형식
     * @param regID    API에 요청할 때 필요한 지역 코드
     * @param dateDays 원하는 날짜의 날씨를 얻기 위해 필요한 날짜의 차이를 계산한 값
     * @brief 생성자에서 변수들을 초기화
     */
    public MiddleLandWeatherConfClass(String date, String regID, int dateDays) {
        logger.info("4일~10일 신뢰도 클래스 객체가 생성됨");
        if (date.matches("^20[0-9]{2}[0-1][0-9][0-3][0-9][0-1][6-8]00$") && regID.matches("^[0-9]{2}[A-Z][0-9]{5}$") && (dateDays >= 3 && dateDays < 11)) {
            logger.info("정확한 값이 들어옴");
            headURL = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleLandWeatherConf";
            urlAdr = "";
            result = "";
            readResult = new StringBuilder("");
            this.regID = regID;
            this.date = date;
            this.dateDays = dateDays;
        } else {
            logger.warn("잘못된 값이 들어옴");
            logger.info("날짜에 {} 지역코드에 {} 날짜 차이에 {}가 들어왔음",date,regID,dateDays);
            headURL = null;
            urlAdr = null;
            result = "잘못된 요청";
            this.regID = null;
            this.date = null;
            this.dateDays = 0;
        }


    }


    /**
     * @brief URL의 정보를 전부 받아서 공백을 없애고 저장하는 함수
     */


    /**
     * setUrl함수와 setResult 함수를 부르고 결과를 파싱 작업과 원하는 결과만 걸러서 result에 저장하는 함수
     */
    public void run() {
        if(regID!=null) {
            int checker = 0;
            setUrl();
            setReadResult();
            StringBuilder resultBuilding = new StringBuilder("");

            try {
                String datedays = Integer.toString(dateDays);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder;


                InputSource is = new InputSource(new StringReader(readResult.toString()));
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
                        if (node.getNodeName().contains(datedays)) {
                            if (dateDays >= 8) {
                                resultBuilding.append("신뢰도 : ");
                                resultBuilding.append(node.getTextContent());
                                break;
                            }
                            if (checker == 0) {
                                resultBuilding.append("오전 신뢰도 : ");
                                resultBuilding.append(node.getTextContent());
                                resultBuilding.append("\n");
                                checker++;
                            } else if (checker == 1) {
                                resultBuilding.append("오후 신뢰도 : ");
                                resultBuilding.append(node.getTextContent());
                                resultBuilding.append("\n");
                            }
                        }


                    }

                }
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
            result = resultBuilding.toString();
        }
    }
}


