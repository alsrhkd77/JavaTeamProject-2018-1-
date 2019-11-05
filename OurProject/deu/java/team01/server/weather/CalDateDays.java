package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author 남영우
 * @brief 날짜를 계산하기 위한 클래스
 * @since 2018-11-11
 */
public class CalDateDays {
    String today;
    String destinationDay;
    private static final Logger logger = LoggerFactory.getLogger(CalDateDays.class);

    /**
     * @param today          오늘의 날짜
     * @param destinationDay 목표로 하는 날짜
     * @brief 생성자에서 초기화 하는 작업
     */
    public CalDateDays(String today, String destinationDay) {
        this.today = today;
        this.destinationDay = destinationDay;
    }

    /**
     * @return 계산한 날짜의 차이
     */
    public int datedays() {
        int result = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date todayDate = format.parse(destinationDay);
            Date destiDate = format.parse(today);
            long calDate = todayDate.getTime() - destiDate.getTime();
            long calDateDay = calDate / (24 * 60 * 60 * 1000);
            result = (int) calDateDay;
        } catch (ParseException e) {
            logger.error("날짜 계산 실패, 잘못된 형태의 문자열");
            logger.error("들어온 오늘 날짜 : {}", today);
            logger.error("목표 날짜 : {}",destinationDay);
        }
        logger.info("계산한 날짜 차이 : {}",result);
        return result;
    }
}
