package deu.java.team01.server.weather;

import deu.java.team01.server.dustozen.DustOzen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 남영우
 * @since 2018-11-11
 */
public class WeatherClassMainPoint {
    private int caldays;
    private List<RegionCode> regionCodes = new LinkedList<>();
    private List<RegionCode> regionCode2s = new LinkedList<>();
    private RegionCode regionCode1;
    private RegionCode regionCode2;
    private StringBuilder resultBuilding;
    private String today;
    private String request;
    private String region;
    private static final Logger logger = LoggerFactory.getLogger(WeatherClassMainPoint.class);

    /**
     * @param request 요청하는 메세지
     */
    public WeatherClassMainPoint(String request) {
        logger.info("날씨 중앙 처리 클래스가 생성됨");
        initialize();//지역코드를 리스트에 넣는다
        initialize2();//지역코드를 리스트에 넣는다
        if (request.matches("^[가-힣]{2,4}-20[0-9]{2}-[0-1][0-9]-[0-3][0-9]$")) {
            logger.info("정확한 값이 들어옴");
            this.request = request;
            String[] parts = request.split("-");//요청 메세지를 -로 끊는다

            String part = parts[0];
            region = parts[0];
            regionCode1 = (regionCodes.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));//중기 육상, 신뢰도
            regionCode2 = (regionCode2s.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));//기온
            GetDate gd = new GetDate();
            today = gd.getDateBuild();//요청시 보낼 때 쓸 거
            String simpleToday = gd.simpleFormToday();// 날짜 계산용
            StringBuilder DestinationBuilder = new StringBuilder("");
            String destinationday = null;

            String dpy = parts[1];
            String dpm = parts[2];
            int dpmi = Integer.parseInt(dpm);
            if (dpmi < 10) {
                DestinationBuilder.append("0");
                DestinationBuilder.append(dpmi);
                dpm = DestinationBuilder.toString();
            }
            DestinationBuilder = new StringBuilder("");
            String dpd = parts[3];
            int dpdi = Integer.parseInt(dpd);
            if (dpdi < 10) {
                DestinationBuilder.append("0");
                DestinationBuilder.append(dpdi);
                dpd = DestinationBuilder.toString();
            }
            StringBuilder destinationBuilder = new StringBuilder("");
            destinationBuilder.append(dpy);
            destinationBuilder.append("-");
            destinationBuilder.append(dpm);
            destinationBuilder.append("-");
            destinationBuilder.append(dpd);
            destinationday = destinationBuilder.toString();
            CalDateDays cd = new CalDateDays(simpleToday, destinationday);
            caldays = cd.datedays();
            resultBuilding = new StringBuilder("");
        } else {
            resultBuilding = new StringBuilder("잘못된 요청");
            logger.warn("잘못된 값이 들어옴");
            logger.warn("{}가 들어옴", request);
            logger.warn("");
            regionCode1 = null;
            regionCode2 = null;
            caldays = -1;
            today = null;
            region = null;
        }


    }

    public void run() {
        if(regionCode1!=null&&regionCode2!=null) {
            if (caldays <= -1) {
                resultBuilding.append("오류 : 과거의 날씨를 요청함");
                logger.warn("전혀 의도치 않게 과거의 날씨가 요청되었다");
                //과거인데 과거는 우리가 처리할 필요도 없고 요청도 하지 않을 것이다 이게 불러지면 오류다

            } else if (caldays < 3) {
                //오늘~모레까지의 날씨를 처리 구현해야 할 부분(완료됨) : 최유래
                if (caldays <= 1) {
                    logger.info("날짜 차이가 {}이어서 미세먼지 클래스가 생성됨", caldays);
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String todayForDust = simpleDateFormat.format(date);
                    DustOzen dustOzen = new DustOzen(todayForDust, region, caldays);
                    dustOzen.run();
                    resultBuilding.append(dustOzen.getResult());
                    logger.info("미세먼지 클래스의 결과는 {}", dustOzen.getResult());
                }
                TodayWeatherClass tWC = new TodayWeatherClass(regionCode2.getCode(), caldays);
                logger.info("날짜 차이가 {}이어서 오늘~3일 날씨 클래스가 생성됨", caldays);
                tWC.append();
                logger.info("오늘~2일 날씨 클래스의 결과는 {}", tWC.getResult());
                resultBuilding.append(tWC.getResult());

            } else if (caldays < 11) {
                //지금 내가 만든 거 3일~10일까지 처리 가능 : 남영우
                logger.info("날짜 차이가 {}이어서 3일~10일 날씨 클래스가 생성됨", caldays);
                resultBuilding.append("날씨\n");
                MiddleLandWeatherClass mL = new MiddleLandWeatherClass(today, regionCode1.getCode(), caldays);
                mL.run();
                logger.info("중기 날씨 파싱 결과는 {}", mL.getResult());
                resultBuilding.append(mL.getResult());


                MiddleTemperatureClass mT = new MiddleTemperatureClass(today, regionCode2.getCode(), caldays);
                mT.run();
                logger.info("중기 온도 파싱 결과는 {}", mT.getResult());
                resultBuilding.append(mT.getResult());


                MiddleLandWeatherConfClass mLW = new MiddleLandWeatherConfClass(today, regionCode1.getCode(), caldays);
                mLW.run();
                logger.info("중기 신뢰도 파싱 결과는 {}", mLW.getResult());
                resultBuilding.append(mLW.getResult());

            } else {
                resultBuilding.append("오류 : 너무나 먼 미래의 날씨를 요청함");
                logger.warn("전혀 의도치 않게 너무나도 먼 미래의 날씨가 요청되었다");
            }
        }
    }

    /**
     * @return 결과값을 반환
     */
    public String getResult() {
        logger.info("총 결과는 {}", resultBuilding.toString());
        return resultBuilding.toString();
    }

    /**
     * @brief 지역코드를 리스트에 초기화
     */
    private void initialize() {
        RegionCode[] data = {//중기 육상, 신뢰도
                new RegionCode("11B00000", "서울, 인천, 경기도, 수원, 파주"),
                new RegionCode("11D10000", "강원도영서, 춘천, 원주"),
                new RegionCode("11D20000", "강원도영동, 영동"),
                new RegionCode("11C20000", "대전, 세종, 충청남도, 서산"),
                new RegionCode("11C10000", "충청북도"),
                new RegionCode("11F20000", "광주, 전라남도, 목포, 여수"),
                new RegionCode("11F10000", "전라북도, 전주, 군산"),
                new RegionCode("11H10000", "대구, 경상북도, 안동, 포항"),
                new RegionCode("11H20000", "부산, 울산, 경상남도, 창원"),
                new RegionCode("11G0000", "제주도, 서귀포")
        };
        regionCodes.addAll(Arrays.asList(data));
    }

    /**
     * @brief 지역코드를 리스트에 초기화
     */
    private void initialize2() {
        RegionCode[] data = {//기온, 오늘~모레
                new RegionCode("11B10101", "서울"),
                new RegionCode("11B20201", "인천"),
                new RegionCode("11B20601", "수원"),
                new RegionCode("11B20305", "파주"),
                new RegionCode("11D10301", "춘천"),
                new RegionCode("11D10401", "원주"),
                new RegionCode("11D20501", "강릉"),
                new RegionCode("11C20401", "대전"),
                new RegionCode("11C20101", "서산"),
                new RegionCode("11C20404", "세종"),
                new RegionCode("11C10301", "청주"),
                new RegionCode("11G00201", "제주"),
                new RegionCode("11G00401", "서귀포"),
                new RegionCode("11F20501", "광주"),
                new RegionCode("21F20801", "목포"),
                new RegionCode("11F20401", "여수"),
                new RegionCode("11F10201", "전주"),
                new RegionCode("21F10501", "군산"),
                new RegionCode("11H20201", "부산"),
                new RegionCode("11H20101", "울산"),
                new RegionCode("11H20301", "창원"),
                new RegionCode("11H10701", "대구"),
                new RegionCode("11H10501", "안동"),
                new RegionCode("11H10201", "포항")

        };
        regionCode2s.addAll(Arrays.asList(data));
    }

}
