package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import static deu.java.team01.server.ServiceKey.SERVICEKEY;

public abstract class AbstractWeather {

    protected String date;
    protected String result;
    protected String regID;
    protected int dateDays;
    protected String urlAdr;
    protected StringBuilder readResult;
    protected String headURL;
    private static final Logger logger = LoggerFactory.getLogger(AbstractWeather.class);

    /**
     * @brief URL의 정보를 전부 받아서 공백을 없애고 저장하는 함수
     */
    protected void setReadResult() {
        if(regID!=null) {
            try {
                URL url = new URL(urlAdr);
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));

                String line;
                while ((line = br.readLine()) != null) {
                    readResult.append(line.trim());
                }
            } catch (MalformedURLException e) {
                logger.error("잘못된 URL : {}", urlAdr);
            } catch (IOException e) {
                logger.error("IOExcention이 발생");
            }
        }
    }

    protected void setUrl() {
        if(regID!=null) {
            StringBuilder urlBuilder = new StringBuilder(headURL);

            urlBuilder.append("?ServiceKey=");
            urlBuilder.append(SERVICEKEY);
            urlBuilder.append("&regId=");
            urlBuilder.append(regID);
            urlBuilder.append("&tmFc=");
            urlBuilder.append(date);
            urlBuilder.append("&numOfRows=1&pageNo=1");
            logger.info("날씨 클래스의 URL : {}", urlBuilder);
            this.urlAdr = urlBuilder.toString();
        }
    }

    public String getResult() {
        logger.info("결과는 : {}", result);
        return result;
    }

}
