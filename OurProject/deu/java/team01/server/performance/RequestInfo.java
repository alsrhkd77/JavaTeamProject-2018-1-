package deu.java.team01.server.performance;


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
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static deu.java.team01.server.ServiceKey.SERVICEKEY;

/* 공연 상세정보 검색 */
public class RequestInfo extends SearchPerfo {

    private String perforSeq;    //공연 일련번호
    private StringBuilder resultUrl;    //URL만들때 사용 할 String
    private String gpsCoordX;
    private String gpsCoordY;
    private String area;
    private String place;
    private StringBuilder mapsUrlFromPlace;
    private StringBuilder mapsUrlFromGPS;
    private StringBuilder resultBuilder = new StringBuilder(""); //파싱 한 아이템 저장용(최종 결과값)
    private static final Logger logger = LoggerFactory.getLogger(RequestInfo.class);

    public String getResult() {
        logger.info("공연 상세 정보가 파싱한 결과물 : {}", resultBuilder.toString());
        return resultBuilder.toString();
    }


    public RequestInfo(String perforSeq) {    //생성자(공연 일련번호 입력)
        logger.info("공연상세 정보 검색 클래스 객체가 생성됨");
        if (perforSeq.matches("^[0-9]{1,7}$")) {
            logger.info("정확한 값이 들어옴");
            resultUrl = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/");
            mapsUrlFromPlace = new StringBuilder("https://www.google.com/maps/search/?api=1");
            mapsUrlFromGPS = new StringBuilder("https://www.google.com/maps/search/?api=1");
            this.perforSeq = perforSeq;
            buildUrl();
            request();
            buildMapsUrlFromPlace();
            buildMapsUrlFromGPS();
        } else {
            logger.warn("잘못된 값이 들어옴");
            logger.info("일련번호에 {}가 들어왔음", perforSeq);
            resultUrl = null;
            mapsUrlFromPlace = null;
            mapsUrlFromGPS = null;
            this.perforSeq = null;
        }
    }

    public String[] getGps() {    //GPS 좌표 획득 함수(반환형: String reference)
        if (mapsUrlFromGPS != null) {
            String[] temp = new String[2];
            temp[0] = this.gpsCoordX;
            temp[1] = this.gpsCoordY;
            return temp;
        } else {
            String temp = "잘못된 요청";
            return temp.split(";");
        }
    }

    public String getArea() {    //지역 획득 함수(ex. 서울 / 인천 / 경기)
        if (perforSeq != null) {
            return area;
        } else {
            return "잘못된 요청";
        }
    }    //지역 획득 함수

    public String getMapsUrlFromPlace() {   //googlemaps URL획득 함수-장소 명 기반
        if (mapsUrlFromPlace != null) {
            return mapsUrlFromPlace.toString();
        } else {
            return "잘못된 요청";
        }
    }

    public String getMapsUrlFromGPS() {  //googlemaps URL획득 함수-GPS기반
        if (mapsUrlFromGPS != null) {
            return mapsUrlFromGPS.toString();
        } else {
            return "잘못된 요청";
        }
    }

    private void buildUrl() {    //URL만들기
        if(resultUrl!=null) {
            resultUrl.append("?ServiceKey=");
            resultUrl.append(SERVICEKEY);    //서비스 키 넣어주기
            resultUrl.append("&ComMsgHeader=");
            resultUrl.append("&RequestTime=");    //옵션필드
            resultUrl.append("&CallBackURI=");
            resultUrl.append("&MsgBody=");
            resultUrl.append("&seq=");
            resultUrl.append(perforSeq);    //공연코드 넣어주기
        }
    }

    private void request() {
        if(resultUrl!=null) {
            try {
                String resultRequest = "";
                resultRequest = request(resultUrl);
                InputSource is = new InputSource(new StringReader(resultRequest));

                DocumentBuilder builder;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                builder = factory.newDocumentBuilder();

                Document doc = builder.parse(is);
                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("/response//msgBody//perforInfo");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList child = nodeList.item(i).getChildNodes();
                    for (int j = 0; j < child.getLength(); j++) {
                        Node node = child.item(j);
                        if (node.getNodeName().contains("title")) {    //제목
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("제목 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("제목: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("startDate")) {    //시작일
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("시작일 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("시작일: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("endDate")) {    //종료일
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("종료일 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("종료일: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().equals("place")) {    //장소
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("장소 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                this.place = node.getTextContent();
                                resultBuilder.append("공연 장소: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("realmName")) {    //장르
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("장르 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("장르: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("area")) {    //지역
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("지역 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                this.area = node.getTextContent();
                                resultBuilder.append("지역: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("price")) {    //가격
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("가격 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("가격: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("url")) {    //티켓구매 링크 주소
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("티켓구매 링크 주소 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("phone")) {    //연락처
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("연락처 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("연락처: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().contains("imgUrl")) {    //포스터 이미지
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("포스터 이미지 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }

                        }
                        if (node.getNodeName().equals("gpsX")) {    //gpsX
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("gpsX 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("gpsX: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                                this.gpsCoordX = node.getTextContent();
                            }
                        }
                        if (node.getNodeName().equals("gpsY")) {    //gpsY
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("gpsY 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("gpsY: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                                this.gpsCoordY = node.getTextContent();
                            }
                        }
                        if (node.getNodeName().equals("placeUrl")) {    //공연장 링크 주소
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("공연장 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                        if (node.getNodeName().equals("placeSeq")) {    //공연장 일련번호
                            if (node.getTextContent().equals("")) {
                                resultBuilder.append("공연장 일련번호 정보 없음");
                                resultBuilder.append("\n");
                            } else {
                                resultBuilder.append("공연장 일련번호: ");
                                resultBuilder.append(node.getTextContent());
                                resultBuilder.append("\n");
                            }
                        }
                    }
                }
                if (resultBuilder.toString().equals("")) {    //공연 일련번호 제대로 못받으면 오류
                    resultBuilder.append("오류");
                    resultBuilder.append("\n");
                }
            } catch (ParserConfigurationException e) {
                logger.error("파싱 설정 오류 발생");
            } catch (IOException e) {
                logger.error("IOException이 발생");
            } catch (XPathExpressionException e) {
                logger.error("XPath표현식이 잘못됐음");
            } catch (SAXException e) {
                logger.error("SAXException이 발생");
            }
        }else{
            resultBuilder = new StringBuilder("잘못된 요청");
        }
    }

        private void buildMapsUrlFromPlace() {    //URL만들기
        if(mapsUrlFromPlace!=null) {
            String buildPlace = place.replace(" ", "+");
            mapsUrlFromPlace.append("&query=");
            mapsUrlFromPlace.append(URLEncoder.encode(buildPlace, StandardCharsets.UTF_8));
            logger.info("장소로 구글맵스 URL은 {}", mapsUrlFromGPS);
        }

    }

    private void buildMapsUrlFromGPS() {    //URL만들기
        if(mapsUrlFromGPS!=null) {
            mapsUrlFromGPS.append("&query=");
            mapsUrlFromGPS.append(URLEncoder.encode(gpsCoordY, StandardCharsets.UTF_8));
            mapsUrlFromGPS.append(",");
            mapsUrlFromGPS.append(URLEncoder.encode(gpsCoordX, StandardCharsets.UTF_8));
            logger.info("GPS로 구글맵스 URL은 {}", mapsUrlFromGPS);
        }
    }
}