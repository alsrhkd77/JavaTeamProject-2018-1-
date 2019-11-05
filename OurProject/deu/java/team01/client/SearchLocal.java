package deu.java.team01.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SearchLocal {
    private String[] local;
    private String getValue = "";
    private static final Logger logger = LoggerFactory.getLogger(SearchLocal.class);
    public SearchLocal() {  //생성자
        searchLocal();
        local = splitValue(getValue);
		local = convertLocal(local);
    }

	public String[] getLocal(){	//결과값 받아오기
        logger.info("지역 : {} 구: {}",local[0],local[1]);
		return local;
	}
	
    private void searchLocal() { //지역 받아오기
        try {
            Document getDoc = Jsoup.connect("https://www.google.co.kr/search?q=%EB%82%A0%EC%94%A8&oq=%EB%82%A0%EC%94%A8&aqs=chrome..69i57j0l5.1620j0j7&sourceid=chrome&ie=UTF-8").get();
            getValue = getDoc.select("#wob_loc").text();
        } catch (IOException e) {
            logger.warn("클라이언트의 지역 받아오는 데서 IOException이 발생");
        }
    }

    private String[] splitValue(String getValue){  //지역을 단어 단위로 나눔
        String[] result = {"",""};
        String[] temp;
        temp = getValue.split(" ");
        if(temp.length == 1){
            result[0] = temp[0];
            result[1] = "없음";
        }
        else{
            result[0] = temp[0];
            result[1] = temp[1];
        }
        return result;
    }

    private String[] convertLocal(String[] local){
        if(local[0].contains("서울")){
            local[0] = "서울";
        }
        if(local[0].contains("부산")){
            local[0] = "부산";
        }
        if(local[0].contains("대구")){
            local[0] = "대구";
        }
        if(local[0].contains("인천")){
            local[0] = "인천";
        }
        if(local[0].contains("광주")){
            local[0] = "광주";
        }
        if(local[0].contains("대전")){
            local[0] = "대전";
        }
        if(local[0].contains("울산")){
            local[0] = "울산";
        }
        if(local[0].contains("제주")){
            local[0] = "제주";
        }

        return local;
    }
}
