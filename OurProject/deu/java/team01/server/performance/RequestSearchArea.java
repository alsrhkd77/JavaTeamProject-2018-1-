package deu.java.team01.server.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deu.java.team01.server.ServiceKey.SERVICEKEY;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/* 지역별 공연 검색 */
public class RequestSearchArea extends SearchPerfo {
    private StringBuilder resultUrl;    // URL만들때 사용 할 String
    private int totalPage;   //총 페이지 수(0일경우 결과값 없음 = 오류)
    private int totalCount;  //총 갯수
    private String fromDate;
    private String toDate;
    private String sido;
    private String gugun;
    private static final Logger logger = LoggerFactory.getLogger(RequestSearchArea.class);
    //TODO: resultBuilder 배열 갯수 클라이언트에서 한 페이지에 표시할 갯수랑 맞추기
    public int getTotalCount(){return totalCount;}
    public int getTotalPage(){return totalPage;}
    private StringBuilder[] resultBuilder = new StringBuilder[4]; //파싱 한 아이템 저장용 배열(최종 결과값)

    public StringBuilder[] getResultBuilder() {

        return resultBuilder;
    }

    /**
     * @param sido
     * @param gugun
     * @param fPage
     */
    public RequestSearchArea(String sido, String gugun, int fPage) {   // 생성자(시도, 구군, 요청할 페이지 넣어야함)(FirstCheck=true 일 경우 마지막 페이지 찾기 실행)
        logger.info("장소로 공연 검색 클래스 객체가 생성됨");
        for (int i = 0; i < 4; i++) {
            resultBuilder[i] = new StringBuilder("");
        }
        fromDate = "20100101";
        toDate = "20501231";
        this.sido = sido;
        this.gugun = gugun;
        run(fPage);
    }

    /**
     * @param fromDate
     * @param toDate
     * @param sido
     * @param gugun
     * @param fPage
     * @override
     */
    public RequestSearchArea(String fromDate, String toDate, String sido, String gugun, int fPage) {   // 생성자(시도, 구군, 요청할 페이지 넣어야함)(FirstCheck=true 일 경우 마지막 페이지 찾기 실행)
        logger.info("장소로 공연 검색 클래스 객체가 생성됨");
        for (int i = 0; i < 4; i++) {
            resultBuilder[i] = new StringBuilder("");
        }
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.sido = sido;
        this.gugun = gugun;
        run(fPage);
    }

    private void run(int fPage) {

        buildUrl(1);
        totalCount = findTotalCount(resultUrl);
        totalPage = calcEOP(totalCount);
        buildUrl(fPage);
        resultBuilder = makeList(resultUrl);
    }

    private void buildUrl(int fPage) {     // URL만들기
        resultUrl = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/area");
        resultUrl.append("?ServiceKey=");
        resultUrl.append(SERVICEKEY);   // 서비스 키 넣어주기
        resultUrl.append("&ComMsgHeader=");
        resultUrl.append("&RequestTime=");
        resultUrl.append("&CallBackURI=");
        resultUrl.append("&MsgBody=");
        resultUrl.append("&sido=");
        resultUrl.append(URLEncoder.encode(sido, StandardCharsets.UTF_8));  //시, 도 넣어주기
        resultUrl.append("&gugun=");
        resultUrl.append(URLEncoder.encode(gugun, StandardCharsets.UTF_8)); //구, 군 넣어주기
        resultUrl.append("&from=");
        resultUrl.append(fromDate);
        resultUrl.append("&to=");
        resultUrl.append(toDate);
        resultUrl.append("&place=1");
        resultUrl.append("&gpsxfrom=124.1");
        resultUrl.append("&gpsyfrom=33.0");
        resultUrl.append("&gpsxto=132.0");
        resultUrl.append("&gpsyto=43.2");
        resultUrl.append("&cPage=");
        resultUrl.append(String.valueOf(fPage));    //요청 할 페이지 번호
        //TODO: 한페이지에 표시 할 줄 수 클라이언트랑 맞추기
        resultUrl.append("&rows=4");    //한 페이지에 표시할 줄 수
        resultUrl.append("&keyword=");
        resultUrl.append("&sortStdr=");
        resultUrl.append("1");    // 정렬기준 넣어주기(1: 등록일, 2: 공연명, 3: 지역)
        logger.info("장소로 공연 검색 URL은 {}",resultUrl);

    }
}