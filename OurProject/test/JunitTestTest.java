import deu.java.team01.server.bus.BusStationByGPS;
import deu.java.team01.server.bus.GetSttnAcctoArvlPrearngeInfoList;


import static org.junit.Assert.*;

public class JunitTestTest {
    @org.junit.Test
    public void test() {


        BusStationByGPS busStationByGPS = new BusStationByGPS("126.976981993", "37.565438157");
        busStationByGPS.run();
        System.out.println(busStationByGPS.getResult());
        String[] parts = busStationByGPS.getResult().split("\\n+");
        String[] results = new String[parts.length / 5];
        for (int i = 0; i < results.length; i++) {
            results[i] = "";
            for (int j = 0; j < 5; j++) {
                results[i] = results[i] + parts[i * 5 + j] + "\n";

            }
        }
        for (String obj : results) {
            System.out.println(obj);
        }
        for (int i = 0; i < results.length; i++) {
            String citycode = results[i].split("\\n+")[0];
            String ID = results[i].split("\\n+")[3];
            GetSttnAcctoArvlPrearngeInfoList getSttnAcctoArvlPrearngeInfoList = new GetSttnAcctoArvlPrearngeInfoList(citycode, ID);
            getSttnAcctoArvlPrearngeInfoList.run();
        }

        BusStationByGPS busStationByGPStest = new BusStationByGPS("가나다라", "마바사");
        busStationByGPStest.run();
        assertEquals("잘못된 요청", busStationByGPStest.getResult());
        BusStationByGPS busStationByGPStest2 = new BusStationByGPS("abcdefg", "hijklmn");
        busStationByGPStest2.run();

        BusStationByGPS busStationByGPStest3 = new BusStationByGPS("123.0", "134.0");
        busStationByGPStest3.run();
        assertEquals("버스 정류소 없음",busStationByGPStest3.getResult());
        BusStationByGPS busStationByGPStest4 = new BusStationByGPS("126.976981993","37.565438157");
        busStationByGPStest4.run();
        assertEquals(busStationByGPStest4.getResult().split("-").length,3);

    }


}