package deu.java.team01.server.performance;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deu.java.team01.server.ServiceKey.SERVICEKEY;
/* 기간별 공연 검색 */
// 지역별 공연 검색으로 대체 가능(지역별 공연검색에 기간으로 공연검색 가능)
public class RequestSearchPeriod extends SearchPerfo {
    private StringBuilder resultUrl;	// URL만들때 사용 할 String
    private int totalPage;   //총 페이지 수(0일경우 결과값 없음 = 오류)
    private int totalCount;  //총 갯수
    private String fromDate;
    private String toDate;
    private static final Logger logger = LoggerFactory.getLogger(RequestSearchPeriod.class);
    //TODO: resultBuilder 배열 갯수 클라이언트에서 한 페이지에 표시할 갯수랑 맞추기
    private StringBuilder[] resultBuilder = new StringBuilder[4]; //파싱 한 아이템 저장용 배열(최종 결과값)
    public StringBuilder[] getResultBuilder(){
        return resultBuilder;
    }
    public int getTotalPage(){
        return totalPage;
    }
    public int getTotalCount(){return totalCount;}
    /**
     *
     * @param fromDate
     * @param toDate
     * @param fPage
     */
    public RequestSearchPeriod(String fromDate, String toDate, int fPage){ //생성자(시작날짜, 종료날짜, 요청할 페이지 넣어줘야함)(FirstCheck=true 일 경우 마지막 페이지 찾기 실행)
        logger.info("기간으로 공연 검색 클래스 객체가 생성됨");
        for(int i=0; i<4; i++){
            resultBuilder[i] = new StringBuilder("");
        }
        this.fromDate = fromDate;
        this.toDate = toDate;
        buildUrl(1);
        totalCount = findTotalCount(resultUrl);
        totalPage = calcEOP(totalCount);
        buildUrl(fPage);
        resultBuilder = makeList(resultUrl);
    }

    public void buildUrl(int fPage) {	// URL만들기
        resultUrl = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period");
        resultUrl.append("?ServiceKey=");
        resultUrl.append(SERVICEKEY);	// 서비스 키 넣어주기
        resultUrl.append("&sortStdr=");
        resultUrl.append("1");	// 정렬기준 넣어주기(1: 등록일, 2: 공연명, 3: 지역)
        resultUrl.append("&ComMsgHeader=");
        resultUrl.append("&RequestTime=");
        resultUrl.append("&CallBackURI=");
        resultUrl.append("&MsgBody=");
        resultUrl.append("&from=");
        resultUrl.append(fromDate);    // 시작날짜 넣어주기
        resultUrl.append("&to=");
        resultUrl.append(toDate);      // 종료날짜 넣어주기
        resultUrl.append("&cPage=");
        resultUrl.append(String.valueOf(fPage));    //요청 할 페이지 번호
        resultUrl.append("&rows=4");    //한 페이지에 표시할 줄 수
        resultUrl.append("&place=");
        resultUrl.append("&gpsxfrom=124.1");
        resultUrl.append("&gpsyfrom=33.0");
        resultUrl.append("&gpsxto=132.0");
        resultUrl.append("&gpsyto=43.2");
        logger.info("기간으로 공연 검색 URL은 {}",resultUrl);
    }
}
