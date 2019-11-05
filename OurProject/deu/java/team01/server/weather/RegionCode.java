package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @brief 중기 육상, 신뢰도 지역코드 리스트를 만들기 위한
 * @author 남영우
 *
 */
public class RegionCode implements Comparable<RegionCode>{
    private String code;
    private String region;
    private static final Logger logger = LoggerFactory.getLogger(RegionCode.class);

    /**
     *
     * @param code 지역 코드
     * @param region 지역명
     */
    public RegionCode(String code, String region){
        this.code = code;
        this.region = region;

    }

    /**
     *
     * @return 지역코드
     */
    public String getCode() {
        return code;
    }


    /**
     *
     * @return 지역명
     */
    public String getRegion() {
        return region;
    }


    /**
     *
     * @param o 비교할 대상
     * @return 똑같으면 0
     */
    @Override
    public int compareTo(RegionCode o) {
        return 0;
    }
}
