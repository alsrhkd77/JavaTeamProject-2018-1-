package test;

import deu.java.team01.server.weather.TodayWeatherClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author 강선모
 * @brief 날씨 class 에서 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
        */
public class JunitTodayWeatherTest {
    @org.junit.Test
    public void test() {
        // 정상작동
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",2);
        todayWeatherTest.append();
        assertEquals("\n"+"오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -8\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 1\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11G00401",1);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 10\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 5\n" +
                "날씨 :구름조금\n" +
                "오후\n" +
                "강수 확률 : 10\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 9\n" +
                "날씨 :구름조금\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11G00401",2);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 10\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 5\n" +
                "날씨 :구름조금\n" +
                "오후\n" +
                "강수 확률 : 10\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 9\n" +
                "날씨 :구름조금\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",1);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -8\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 1\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",1	);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -8\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 1\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",0);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -8\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 1\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",2);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -8\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 1\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11C20404",1);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 10\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -9\n" +
                "날씨 :구름조금\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 3\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11H20201",1);
        todayWeatherTest.append();
        assertEquals("\n" +
                "오전\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : -3\n" +
                "날씨 :맑음\n" +
                "오후\n" +
                "강수 확률 : 0\n" +
                "강수 형태 : 강수없음\n" +
                "예상 기온 : 7\n" +
                "날씨 :맑음\n", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",4);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11G00401",5	);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11G00401",9);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11B10101",6);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11Z00401",7);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11Z10101",9);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11X20404",8);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11Z10101",3);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11b20516",1);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11q20201",1);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        TodayWeatherClass todayWeatherTest = new TodayWeatherClass("11C20404",8);
        todayWeatherTest.append();
        assertEquals("잘못된 요청", todayWeatherTest.getResult());
    }
}
