package test;

import deu.java.team01.server.weather.MiddleLandWeatherClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author 강선모
 * @brief 날씨 class 에서 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
 */
public class JunitMiddleWeatherTest {
    @org.junit.Test
    public void test() {
        // 정상작동
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201812120600","11B00000",4);
        weatherTest.run();
        assertEquals("\n" +
                "오전 : 구름많고 비/눈\n" +
                "오후 : 구름많음\n", weatherTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201811220600","11D10000",5);
        weatherTest.run();
        assertEquals("\n", weatherTest.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201706160600","11D20000",8);
        weatherTest.run();
        assertEquals("\n", weatherTest.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201812160600","11F20000",5);
        weatherTest.run();
        assertEquals("\n", weatherTest.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("201812220600","11F10000",6);
        weatherTest.run();
        assertEquals("\n", weatherTest.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20180216","11G0000",7);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20180816","11H20000",9);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20171130","11C20404",8);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20171231","11H20201",6);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20160101","11H20201",7);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("21181212","11B10101",4);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("22181122","11G00401",5);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("25181216","11G00401",5);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20171130","11X20404",8);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20170616","11B10101",16);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20221212","11B10101",2);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20311122","11G00401",5);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20180216","11G12345",7);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20180816","11B67876",9);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        MiddleLandWeatherClass weatherTest = new MiddleLandWeatherClass("20170616","11Z10101",3);
        weatherTest.run();
        assertEquals("잘못된 요청", weatherTest.getResult());
    }
}
