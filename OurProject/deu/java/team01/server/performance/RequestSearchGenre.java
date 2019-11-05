package deu.java.team01.server.performance;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deu.java.team01.server.ServiceKey.SERVICEKEY;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/* 장르별 공연 검색 */
public class RequestSearchGenre extends SearchPerfo {
    private StringBuilder resultUrl;    // URL만들때 사용 할 String
    private int totalPage;   //총 페이지 수(0일경우 결과값 없음 = 오류)
    private int totalCount;  //총 갯수
    private String fromDate;
    private String toDate;
    private String sido;
    private String gugun;
    private String genre;
    private static final Logger logger = LoggerFactory.getLogger(RequestSearchGenre.class);

    public int getTotalPage() {
        return totalPage;
    }

    //TODO: resultBuilder 배열 갯수 클라이언트에서 한 페이지에 표시할 갯수랑 맞추기
    public int getTotalCount() {
        return totalCount;
    }

    private StringBuilder[] resultBuilder = new StringBuilder[4]; //파싱 한 아이템 저장용 배열(최종 결과값)

    public StringBuilder[] getResultBuilder() {
        return resultBuilder;
    }

    /**
     * @param genre
     * @param fPage
     */
    public RequestSearchGenre(String fromDate, String toDate, String sido, String gugun, String genre, int fPage) {    //생성자(장르명, 요청할 페이지)(FirstCheck=true 일 경우 마지막 페이지 찾기 실행)
        logger.info("장르로 공연 검색 클래스 객체가 생성됨!");
        int result = -1;
        if (fromDate.matches("^20[0-9]{2}[0-1][0-9][0-3][0-9]$") && toDate.matches("^20[0-9]{2}[0-1][0-9][0-3][0-9]$") && fPage > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date startDate = dateFormat.parse(fromDate);
                Date endDate = dateFormat.parse(toDate);
                long calDate = endDate.getTime() - startDate.getTime();
                long calDateDay = calDate / (24 * 60 * 60 * 1000);
                result = (int) calDateDay;
            } catch (ParseException e) {
                logger.error("날짜 계산을 위한 파싱이 실패");
                logger.error("값이 잘못 들어왔음! 시작 날짜 {}, 끝 날짜 {}", fromDate, toDate);
            }
            if (result >= 0) {
                for (int i = 0; i < 4; i++) {
                    resultBuilder[i] = new StringBuilder("");
                }
                this.fromDate = fromDate;
                this.toDate = toDate;
                this.sido = sido;
                this.gugun = gugun;
                this.genre = convertGenre(genre);   //한글로 받은 장르 변환
                buildUrl(1);
                totalCount = findTotalCount(resultUrl);
                totalPage = calcEOP(totalCount);
                buildUrl(fPage);
                resultBuilder = makeList(resultUrl);
            }
        } else {
            logger.error("값이 잘못 들어왔음! 시작 날짜 {}, 끝 날짜 {}, 시도 {}, 구군 {}, 장르 {}, 페이지", fromDate, toDate, sido, gugun, genre, fPage);
            resultUrl=null;
            this.fromDate = null;
            this.toDate = null;
            this.sido = null;
            this.gugun = null;
            this.genre = null;
        }
    }

    private String convertGenre(String insert) { //장르 변환해주는 함수(문자->코드)
        String result;
        switch (insert) {
            case "연극":    //연극
                result = "A000";
                break;
            case "음악":    //음악(콘서트, 뮤지컬 등)
                result = "B000";
                break;
            case "무용":    //무용
                result = "C000";
                break;
            case "미술":    //미술
                result = "D000";
                break;
            case "건축":    //건축
                result = "E000";
                break;
            case "영상":    //영상
                result = "G000";
                break;
            case "문학":    //문학
                result = "H000";
                break;
            case "문화정책":    //문화정책
                result = "I000";
                break;
            case "축제문화공간":    //축제문화공간
                result = "J000";
                break;
            case "기타":    //기타
                result = "L000";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    private void buildUrl(int fPage) {    //URL만들기
        if(fromDate!=null&&toDate!=null&&sido!=null&&gugun!=null&&genre!=null) {
            resultUrl = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm");
            resultUrl.append("?ServiceKey=");
            resultUrl.append(SERVICEKEY);   // 서비스 키 넣어주기
            resultUrl.append("&sido=");
            resultUrl.append(URLEncoder.encode(sido, StandardCharsets.UTF_8));  //시, 도 넣어주기
            resultUrl.append("&gugun=");
            resultUrl.append(URLEncoder.encode(gugun, StandardCharsets.UTF_8)); //구, 군 넣어주기
            resultUrl.append("&place=1");
            resultUrl.append("&gpsxfrom=124.1");
            resultUrl.append("&gpsyfrom=33.0");
            resultUrl.append("&gpsxto=132.0");
            resultUrl.append("&gpsyto=43.2");
            resultUrl.append("&keyword=");
            resultUrl.append("&sortStdr=1");
            resultUrl.append("&ComMsgHeader=");
            resultUrl.append("&RequestTime=");
            resultUrl.append("&CallBackURI=");
            resultUrl.append("&MsgBody=");
            resultUrl.append("&realmCode=");
            resultUrl.append(genre);    // 입력한 장르
            resultUrl.append("&cPage=");
            resultUrl.append(String.valueOf(fPage));    //요청 할 페이지 번호
            resultUrl.append("&rows=4");    //한 페이지에 표시할 줄 수
            //TODO: 한페이지에 표시 할 줄 수 클라이언트랑 맞추기
            resultUrl.append("&from=");
            resultUrl.append(fromDate);    // 시작날짜 넣어주기
            resultUrl.append("&to=");
            resultUrl.append(toDate);      // 종료날짜 넣어주기
            logger.info("장르로 공연 검색 URL은 {}", resultUrl.toString());
        }
    }
}
