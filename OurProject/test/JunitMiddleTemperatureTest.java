package test;

import deu.java.team01.server.weather.MiddleTemperatureClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author 강선모
 * @brief 날씨 class 에서 최고 온도와 최저 온도 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
 */
public class JunitMiddleTemperatureTest {
    @org.junit.Test
    public void test() {
        // 정상작동
        MiddleTemperatureClass weatherTest = new MiddleTemperatureClass("201812120600","11B10101",3);
        weatherTest.run();
        assertEquals("최고 온도 : 3\n" +
                "최저 온도 : -7\n", weatherTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        MiddleTemperatureClass weatherTest2 = new MiddleTemperatureClass("201812120600","11B20201",5);
        weatherTest2.run();
        assertEquals("최고 온도 : 5\n" +
                "최저 온도 : -1\n", weatherTest2.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        MiddleTemperatureClass weatherTest3 = new MiddleTemperatureClass("201812120600","11B20601",8);
        weatherTest3.run();
        assertEquals("최고 온도 : 4\n" +
                "최저 온도 : -4\n", weatherTest3.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        MiddleTemperatureClass weatherTest4 = new MiddleTemperatureClass("201812120600","11C20101",5);
        weatherTest4.run();
        assertEquals("최고 온도 : 5\n" +
                "최저 온도 : -1\n", weatherTest4.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        MiddleTemperatureClass weatherTest5 = new MiddleTemperatureClass("201812120600","11C10301",6);
        weatherTest5.run();
        assertEquals("최고 온도 : 7\n" +
                "최저 온도 : -5\n", weatherTest5.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        MiddleTemperatureClass weatherTest6 = new MiddleTemperatureClass("20180216","21F20801",7);
        weatherTest6.run();
        assertEquals("잘못된 요청", weatherTest6.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        MiddleTemperatureClass weatherTest7 = new MiddleTemperatureClass("20180816","21F10501",9);
        weatherTest7.run();
        assertEquals("잘못된 요청", weatherTest7.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        MiddleTemperatureClass weatherTest8 = new MiddleTemperatureClass("20171130","11H20201",8);
        weatherTest8.run();
        assertEquals("잘못된 요청", weatherTest8.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        MiddleTemperatureClass weatherTest9 = new MiddleTemperatureClass("20171231","11H20201",6);
        weatherTest9.run();
        assertEquals("잘못된 요청", weatherTest9.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        MiddleTemperatureClass weatherTest10 = new MiddleTemperatureClass("20160101","11H10701",7);
        weatherTest10.run();
        assertEquals("잘못된 요청", weatherTest10.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        MiddleTemperatureClass weatherTest11 = new MiddleTemperatureClass("21181212","11H10501",4);
        weatherTest11.run();
        assertEquals("잘못된 요청", weatherTest11.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        MiddleTemperatureClass weatherTest12 = new MiddleTemperatureClass("22181122","11F20401",5);
        weatherTest12.run();
        assertEquals("잘못된 요청", weatherTest12.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        MiddleTemperatureClass weatherTest13 = new MiddleTemperatureClass("25181216","21F10501",5);
        weatherTest13.run();
        assertEquals("잘못된 요청", weatherTest13.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        MiddleTemperatureClass weatherTest14 = new MiddleTemperatureClass("20171130","11H20301",8);
        weatherTest14.run();
        assertEquals("잘못된 요청", weatherTest14.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        MiddleTemperatureClass weatherTest15 = new MiddleTemperatureClass("20170616","11C20101",16);
        weatherTest15.run();
        assertEquals("잘못된 요청", weatherTest15.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        MiddleTemperatureClass weatherTest16 = new MiddleTemperatureClass("20221212","11C10301",2);
        weatherTest16.run();
        assertEquals("잘못된 요청", weatherTest16.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        MiddleTemperatureClass weatherTest17 = new MiddleTemperatureClass("20311122","11F20401",5);
        weatherTest17.run();
        assertEquals("잘못된 요청", weatherTest17.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        MiddleTemperatureClass weatherTest18 = new MiddleTemperatureClass("20180216","11G12345",7);
        weatherTest18.run();
        assertEquals("잘못된 요청", weatherTest18.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        MiddleTemperatureClass weatherTest19 = new MiddleTemperatureClass("20180816","11B67876",9);
        weatherTest19.run();
        assertEquals("잘못된 요청", weatherTest19.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        MiddleTemperatureClass weatherTest20 = new MiddleTemperatureClass("20170616","11Z10101",3);
        weatherTest20.run();
        assertEquals("잘못된 요청", weatherTest20.getResult());
    }
}
