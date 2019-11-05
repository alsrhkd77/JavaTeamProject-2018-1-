package deu.java.team01.server.bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BusTestMain {
    private static final Logger logger = LoggerFactory.getLogger(BusTestMain.class);


    public static void main(String[] args) {
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
        logger.info(busStationByGPStest.getResult());
        BusStationByGPS busStationByGPStest2 = new BusStationByGPS("abcdefg", "hijklmn");
        busStationByGPStest2.run();
        logger.info(busStationByGPStest2.getResult());
        BusStationByGPS busStationByGPStest3 = new BusStationByGPS("123.0", "134.0");
        busStationByGPStest3.run();
        logger.info(busStationByGPStest3.getResult());
    }
}
