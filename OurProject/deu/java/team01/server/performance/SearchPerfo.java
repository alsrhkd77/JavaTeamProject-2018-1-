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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/* 공통 사용 코드(데이터 받기, 파싱) */
public abstract class SearchPerfo {
    private static final Logger logger = LoggerFactory.getLogger(SearchPerfo.class);

    public String request(StringBuilder resultUrl) {
        if (resultUrl != null) {
            StringBuilder resultRequest = new StringBuilder("");
            try {
                URL url = new URL(resultUrl.toString());    //URL객체 생성
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();    //url연결
                BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));    //값 받기
                String line;    //받은거 읽을 때 사용
                while ((line = br.readLine()) != null) {    //받은거 한줄씩 line으로 받기
                    resultRequest.append(line.trim());    //받은거 한줄씩추가
                }
            } catch (MalformedURLException e) {
                logger.error("잘못된 URL : {}", resultUrl.toString());
            } catch (IOException e) {
                logger.error("IOExcention이 발생");
            }

            return resultRequest.toString();
        } else {
            return null;
        }
    }

    public int findTotalCount(StringBuilder resultUrl) {  //totalCount 찾기(총 갯수 찾기)
        if (resultUrl != null) {
            int totalCount = 0;
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
                XPathExpression expr = xpath.compile("/response//msgBody//totalCount");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                Node node = nodeList.item(0);
                if (node.getNodeName().equals("totalCount")) {    //총 갯수 적힌 노드 맞는지 확인
                    totalCount = Integer.parseInt(node.getTextContent());
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
            return totalCount;
        } else {
            return 0;
        }
    }

    public int calcEOP(int totalCount) { //마지막 페이지 계산하기
        int countPage;
        //TODO 한 페이지에 표현할 갯수에 맞춰서 바꿔줄 것
        if (totalCount % 4 == 0) {
            countPage = totalCount / 4;
        } else {
            countPage = (totalCount / 4) + 1;
        }
        return countPage;
    }

    public StringBuilder[] makeList(StringBuilder resultUrl) {
        if (resultUrl != null) {
            StringBuilder[] resultBuilder = new StringBuilder[4]; //파싱 한 아이템 저장용 배열(최종 결과값)
            for (int i = 0; i < 4; i++) {
                resultBuilder[i] = new StringBuilder("");
            }
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
                XPathExpression expr = xpath.compile("/response//msgBody//perforList");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList child = nodeList.item(i).getChildNodes();
                    for (int j = 0; j < child.getLength(); j++) {
                        Node node = child.item(j);
                        if (node.getNodeName().contains("seq")) {    //공연 일련번호
                            //resultBuilder[i].append("일련번호: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("title")) {    //공연 제목
                            //resultBuilder[i].append("제목: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("startDate")) {    //공연 시작일
                            resultBuilder[i].append("시작일: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("endDate")) {    //공연 종료일
                            resultBuilder[i].append("종료일: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("place")) {    //공연 장소
                            resultBuilder[i].append("장소: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("realmName")) {    //공연 장르
                            resultBuilder[i].append("장르: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                        if (node.getNodeName().contains("area")) {    //공연 지역
                            resultBuilder[i].append("지역: ");
                            resultBuilder[i].append(node.getTextContent());
                            resultBuilder[i].append("\n");
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("IOException이 발생");
            } catch (ParserConfigurationException e) {
                logger.error("파싱 설정 오류 발생");
            } catch (SAXException | XPathExpressionException e) {
                logger.error("SAXException이 발생");
            }
            return resultBuilder;
        } else {
            return null;
        }
    }


}
