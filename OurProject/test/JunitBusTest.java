package test;
import deu.java.team01.server.bus.BusStationByGPS;
import static org.junit.Assert.assertEquals;
/**
 * @author 강선모
 * @brief 버스정류소 class 에서 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
 */
public class JunitBusTest {
    @org.junit.Test
    public void test() {
        // 완전 잘못된 값
        BusStationByGPS busStationByGPStest = new BusStationByGPS("가나다라", "마바사");
        busStationByGPStest.run();
        assertEquals("잘못된 요청", busStationByGPStest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
}
    @org.junit.Test
    public void test2()	// 정규식 GPS좌표가 들어왔을 시
    {
        BusStationByGPS busStationByGPStest2 = new BusStationByGPS("123.0", "134.0");
        busStationByGPStest2.run();
        assertEquals("버스 정류소 없음", busStationByGPStest2.getResult());
    }
    @org.junit.Test
    public void test3()	// 정규식 GPS좌표가 들어왔을 시
    {
        BusStationByGPS busStationByGPStest3 = new BusStationByGPS("127.00372190765472", "37.58225110373835");
        busStationByGPStest3.run();
        assertEquals("성대입구-명륜3가성대입구-혜화동로터리-", busStationByGPStest3.getResult());
    }
    @org.junit.Test
    public void test4()	// 정규식 GPS좌표가 들어왔을 시
    {
        BusStationByGPS busStationByGPStest4 = new BusStationByGPS("126.976981993", "37.565438157");
        busStationByGPStest4.run();
        assertEquals("시청역-서울시청-서울플라자호텔-", busStationByGPStest4.getResult());
    }
    @org.junit.Test
    public void test5()	// 정규식 GPS좌표가 들어왔을 시
    {
        BusStationByGPS busStationByGPStest5 = new BusStationByGPS("127.042914261", "127.042914261");
        busStationByGPStest5.run();
        assertEquals("버스 정류소 없음", busStationByGPStest5.getResult());
    }
    @org.junit.Test
    public void test6()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest6 = new BusStationByGPS("127.001000671", "37.5718122377");
        busStationByGPStest6.run();
        assertEquals("동대문종합시장종로6가-동대문종합시장종로6가-종로4가-", busStationByGPStest6.getResult());
    }
    @org.junit.Test
    public void test7()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest7 = new BusStationByGPS("DEUUNIVERSITY", "SEOULUNIVERSITY");
        busStationByGPStest7.run();
        assertEquals("잘못된 요청", busStationByGPStest7.getResult());
    }
    @org.junit.Test
    public void test8()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest8 = new BusStationByGPS("NEEDA++", "ILOVEIT");
        busStationByGPStest8.run();
        assertEquals("잘못된 요청", busStationByGPStest8.getResult());
    }
    @org.junit.Test
    public void test9()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest9 = new BusStationByGPS("1A22D3.A2D1", "A2X1Z22F3.Z5X6C");
        busStationByGPStest9.run();
        assertEquals("잘못된 요청", busStationByGPStest9.getResult());
    }
    @org.junit.Test
    public void test10()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest10 = new BusStationByGPS("ASDFGHJK","QWERTYU");
        busStationByGPStest10.run();
        assertEquals("잘못된 요청", busStationByGPStest10.getResult());
    }
    @org.junit.Test
    public void test11()	// 이상한 문자 (  )
    {
        BusStationByGPS busStationByGPStest11 = new BusStationByGPS("127.17751611581883", "37.24101911606393");
        busStationByGPStest11.run();
        assertEquals("버스 정류소 없음", busStationByGPStest11.getResult());
    }
    @org.junit.Test
    public void test12(){	// 이상한 문자 ( 영어가 들어왔을 시 )
        BusStationByGPS busStationByGPStest12 = new BusStationByGPS("126.985722923", "37.574590047");
        busStationByGPStest12.run();
        assertEquals("안국역-안국역-창덕궁-", busStationByGPStest12.getResult());
    }
    @org.junit.Test
    public void test13()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest13 = new BusStationByGPS("127.064832434", "37.5109481316");
        busStationByGPStest13.run();
        assertEquals("한국무역센타-", busStationByGPStest13.getResult());
    }
    @org.junit.Test
    public void test14()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest14 = new BusStationByGPS("129.12843429", "35.1720596687");
        busStationByGPStest14.run();
        assertEquals("벤처타운-벤처타운-SK텔레콤-", busStationByGPStest14.getResult());
    }
    @org.junit.Test
    public void test15()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest15 = new BusStationByGPS("127.021165681", "37.2744384118");
        busStationByGPStest15.run();
        assertEquals("버스 정류소 없음", busStationByGPStest15.getResult());
    }
    @org.junit.Test
    public void test16()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest16 = new BusStationByGPS("126.93797557422386", "37.56199626820579");
        busStationByGPStest16.run();
        assertEquals("세브란스병원-세브란스병원앞-연대앞.세브란스병원-", busStationByGPStest16.getResult());
    }
    @org.junit.Test
    public void test17()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest17 = new BusStationByGPS("127.42799847566087", "36.32565033005217");
        busStationByGPStest17.run();
        assertEquals("대흥동성당-대흥동성당-대흥동성당-", busStationByGPStest17.getResult());
    }
    @org.junit.Test
    public void test18()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest18 = new BusStationByGPS("126.857555597", "35.1583045515");
        busStationByGPStest18.run();
        assertEquals("5.18기념문화센터-5.18기념문화센터-중흥아파트사거리-", busStationByGPStest18.getResult());
    }
    @org.junit.Test
    public void test19()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest19 = new BusStationByGPS("129.0323193330811", "35.09834125605269");
        busStationByGPStest19.run();
        assertEquals("남포동-남포동-자갈치입구-", busStationByGPStest19.getResult());
    }
    @org.junit.Test
    public void test20()	// 이상한 문자 ( 영어가 들어왔을 시 )
    {
        BusStationByGPS busStationByGPStest20 = new BusStationByGPS("126.983445001", "37.5759052016");
        busStationByGPStest20.run();
        assertEquals("안국역-안국역-", busStationByGPStest20.getResult());
    }





}