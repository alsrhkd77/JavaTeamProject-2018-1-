package test;

import deu.java.team01.server.weather.MiddleLandWeatherClass;
import static org.junit.Assert.assertEquals;
/**
 * @author 강선모
 * @brief 날씨 class 에서 오전과 오후 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
 */
public class JunitLandMiddleTemperature {
    @org.junit.Test
    public void test() {
        // 정상작동
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201812120600","11B00000",3);
        weatherTest.run();
        assertEquals("\n" +
                "오전 : 맑음\n" +
                "오후 : 구름많음\n", weatherTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest2 = new MiddleLandWeatherClass("201812120600","11D20000",5);
        weatherTest2.run();
        assertEquals("\n" +
                "오전 : 구름조금\n" +
                "오후 : 구름조금\n", weatherTest2.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest3 = new MiddleLandWeatherClass("201812120600","11C20000",8);
        weatherTest3.run();
        assertEquals("\n" +
                "날씨 : 구름조금\n", weatherTest3.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest4 = new MiddleLandWeatherClass("201812120600","11C10000",5);
        weatherTest4.run();
        assertEquals("\n" +
                "오전 : 구름조금\n" +
                "오후 : 구름조금\n", weatherTest4.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest5 = new MiddleLandWeatherClass("201812120600","11C20000",6);
        weatherTest5.run();
        assertEquals("\n" +
                "오전 : 구름조금\n" +
                "오후 : 구름조금\n", weatherTest5.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest6 = new MiddleLandWeatherClass("20180216","11F20000",7);
        weatherTest6.run();
        assertEquals("잘못된 요청", weatherTest6.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest7 = new MiddleLandWeatherClass("20180816","11F10000",9);
        weatherTest7.run();
        assertEquals("잘못된 요청", weatherTest7.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest8 = new MiddleLandWeatherClass("20171130","11H10000",8);
        weatherTest8.run();
        assertEquals("잘못된 요청", weatherTest8.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest9 = new MiddleLandWeatherClass("20171231","11H20000",6);
        weatherTest9.run();
        assertEquals("잘못된 요청", weatherTest9.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest10 = new MiddleLandWeatherClass("20160101","11D10000",7);
        weatherTest10.run();
        assertEquals("잘못된 요청", weatherTest10.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest11 = new MiddleLandWeatherClass("21181212","11D20000",4);
        weatherTest11.run();
        assertEquals("잘못된 요청", weatherTest11.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        MiddleLandWeatherClass weatherTest12 = new MiddleLandWeatherClass("22181122","11C20000",5);
        weatherTest12.run();
        assertEquals("잘못된 요청", weatherTest12.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest13 = new MiddleLandWeatherClass("25181216","11C10000",5);
        weatherTest13.run();
        assertEquals("잘못된 요청", weatherTest13.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest14 = new MiddleLandWeatherClass("20171130","11F20000",8);
        weatherTest14.run();
        assertEquals("잘못된 요청", weatherTest14.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest15 = new MiddleLandWeatherClass("20170616","11B00000",16);
        weatherTest15.run();
        assertEquals("잘못된 요청", weatherTest15.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest16 = new MiddleLandWeatherClass("20221212","11D10000",2);
        weatherTest16.run();
        assertEquals("잘못된 요청", weatherTest16.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        MiddleLandWeatherClass weatherTest17 = new MiddleLandWeatherClass("20311122","11D20000",5);
        weatherTest17.run();
        assertEquals("잘못된 요청", weatherTest17.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest18 = new MiddleLandWeatherClass("20180216","11C20000",7);
        weatherTest18.run();
        assertEquals("잘못된 요청", weatherTest18.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest19 = new MiddleLandWeatherClass("20180816","11C10000",9);
        weatherTest19.run();
        assertEquals("잘못된 요청", weatherTest19.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        MiddleLandWeatherClass weatherTest20 = new MiddleLandWeatherClass("20170616","11Z10101",3);
        weatherTest20.run();
        assertEquals("잘못된 요청", weatherTest20.getResult());
    }
}
