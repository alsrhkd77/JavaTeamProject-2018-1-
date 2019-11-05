package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherClassTestDrive {
    private static final Logger logger = LoggerFactory.getLogger(WeatherClassTestDrive.class);
    public static void main(String[] args) {
        WeatherClassMainPoint weatherClassMainPoint = new WeatherClassMainPoint("서울-2018-12-10");
        weatherClassMainPoint.run();
        System.out.println(weatherClassMainPoint.getResult());
        MiddleTemperatureClass middleTemperatureClass = new MiddleTemperatureClass("201812120600","11B10101",4);
        middleTemperatureClass.run();
        System.out.println(middleTemperatureClass.getResult());


    }
}
