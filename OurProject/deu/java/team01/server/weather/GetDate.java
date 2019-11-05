package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author 남영우
 * @brief 시간과 날짜를 계산하여 날짜 요청 할 때 넣을 String 형태의 값을 만드는 클래스
 * @since 2018-11-11
 */
public class GetDate {
    private Calendar cal;
    private int month;
    private int date;
    private int year;
    private int hour;
    String dateBuild;
    private static final Logger logger = LoggerFactory.getLogger(GetDate.class);
    /**
     * @brief 생성자 초기화 하는 작업
     */
    public GetDate() {
        logger.info("GetDate 객체가 생성됨");
        cal = Calendar.getInstance();//컴퓨타에서 시간 가져오는 방법
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        /*
        TimeZone time; //서버에서 시간 가져오는 방법
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");

        time = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(time);
        System.out.format("%s%n%s%n%n", time.getDisplayName(), df.format(date));
        */
        //System.out.println(hour);

    }

    /**
     *
     * @return 날짜 계산할 때 필요한 형태를 만들어서 return;
     */
    public String simpleFormToday() {

        String simpleToday;
        StringBuilder simpleTodayBuilder = new StringBuilder("");
        simpleTodayBuilder.append(year);
        simpleTodayBuilder.append("-");
        if (month < 10)
            simpleTodayBuilder.append("0");
        simpleTodayBuilder.append(month);
        simpleTodayBuilder.append("-");
        if (date < 10)
            simpleTodayBuilder.append("0");
        simpleTodayBuilder.append(date);
        simpleToday = simpleTodayBuilder.toString();
        logger.info("날짜 계산용 String : {}",simpleToday);
        return simpleToday;
    }

    /**
     * @return API에 요청 시 보낼 형태의 String
     */
    public String getDateBuild() {
        StringBuilder resultBuilding = new StringBuilder("");
        if (hour < 6) {
            Calendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(Calendar.DATE, -1);
            year = gregorianCalendar.get(Calendar.YEAR);
            month = (gregorianCalendar.get(Calendar.MONTH) + 1);
            date = gregorianCalendar.get(Calendar.DATE);
        }
        resultBuilding.append(year);
        if (month < 10)
            resultBuilding.append("0");
        resultBuilding.append(month);
        if (date < 10) {
            resultBuilding.append("0");
        }
        resultBuilding.append(date);
        if (hour < 6 || hour >= 18) {
            resultBuilding.append("1800");
        } else if (hour >= 6 || hour < 18) {
            resultBuilding.append("0600");
        }
        dateBuild = resultBuilding.toString();
        logger.info("날씨 API 요청용 String {}",dateBuild);
        return dateBuild;
    }


}
