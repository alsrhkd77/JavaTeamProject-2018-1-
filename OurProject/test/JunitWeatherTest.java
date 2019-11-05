package test;

import deu.java.team01.server.weather.TodayWeatherClass;
import org.junit.Test;
/**
 * @author 강선모
 * @brief 날씨 class 에서 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리, 완전히 잘못된 값, 정규표현식이지만 잘못된 값
        */
public class JunitWeatherTest {
    @org.junit.Test
    public void test() {
        // 정상작동
        TodayWeatherClass TodayWeatherTest = new TodayWeatherClass("2018-12-11","부산",0);
        TodayWeatherTest.run();
        assertEquals("부산 : 좋음", TodayWeatherTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        TodayWeatherClass TodayWeatherTest2 = new TodayWeatherClass("2018-12-11","부산",1);
        TodayWeatherTest2.run();
        assertEquals("정보없음", TodayWeatherTest2.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        TodayWeatherClass TodayWeatherTest3 = new TodayWeatherClass("2018-12-11","제주",0);
        TodayWeatherTest3.run();
        assertEquals("제주 : 좋음", TodayWeatherTest3.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        TodayWeatherClass TodayWeatherTest4 = new TodayWeatherClass("2018-12-11","제주",1);
        TodayWeatherTest4.run();
        assertEquals("정보없음", TodayWeatherTest4.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        TodayWeatherClass TodayWeatherTest5 = new TodayWeatherClass("2018-12-11","전북",0);
        TodayWeatherTest5.run();
        assertEquals("전북 : 보통", TodayWeatherTest5.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        TodayWeatherClass TodayWeatherTest6 = new TodayWeatherClass("2018-12-11","전북",0);
        TodayWeatherTest6.run();
        assertEquals("전북 : 보통", TodayWeatherTest6.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        TodayWeatherClass TodayWeatherTest7 = new TodayWeatherClass("2018-12-11","광주",0);
        TodayWeatherTest7.run();
        assertEquals("광주 : 좋음", TodayWeatherTest7.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        TodayWeatherClass TodayWeatherTest8 = new TodayWeatherClass("2018-12-11","서울",0);
        TodayWeatherTest8.run();
        assertEquals("서울 : 보통", TodayWeatherTest8.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        TodayWeatherClass TodayWeatherTest9 = new TodayWeatherClass("2018-12-11","서울",1);
        TodayWeatherTest9.run();
        assertEquals("정보없음", TodayWeatherTest9.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        TodayWeatherClass TodayWeatherTest10 = new TodayWeatherClass("2111-12-25","SEUOL",2);
        TodayWeatherTest10.run();
        assertEquals("잘못된 요청", TodayWeatherTest10.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        TodayWeatherClass TodayWeatherTest11 = new TodayWeatherClass("2111-07-12","BUSAN",12);
        TodayWeatherTest11.run();
        assertEquals("잘못된 요청", TodayWeatherTest11.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        TodayWeatherClass TodayWeatherTest12 = new TodayWeatherClass("2020-03-15","INCHEON",15);
        TodayWeatherTest12.run();
        assertEquals("잘못된 요청", TodayWeatherTest12.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        TodayWeatherClass TodayWeatherTest13 = new TodayWeatherClass("2103-15-13","DEAGU",33);
        TodayWeatherTest13.run();
        assertEquals("잘못된 요청", TodayWeatherTest13.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        TodayWeatherClass TodayWeatherTest14 = new TodayWeatherClass("2051-51-61","JEJU",15);
        TodayWeatherTest14.run();
        assertEquals("잘못된 요청", TodayWeatherTest14.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass TodayWeatherTest15 = new TodayWeatherClass("2018-11-12","SEUOL",1);
        TodayWeatherTest15.run();
        assertEquals("잘못된 요청", TodayWeatherTest15.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass TodayWeatherTest16 = new TodayWeatherClass("2018-12-11","서울",2);
        TodayWeatherTest16.run();
        assertEquals("잘못된 요청", TodayWeatherTest16.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        TodayWeatherClass TodayWeatherTest17 = new TodayWeatherClass("2018-12-39","서울",1);
        TodayWeatherTest17.run();
        assertEquals("정보없음", TodayWeatherTest17.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass TodayWeatherTest18 = new TodayWeatherClass("2018-06-26","광주",3);
        TodayWeatherTest18.run();
        assertEquals("잘못된 요청", TodayWeatherTest18.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        TodayWeatherClass TodayWeatherTest19 = new TodayWeatherClass("2017-11-11","인천",5);
        TodayWeatherTest19.run();
        assertEquals("잘못된 요청", TodayWeatherTest19.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        TodayWeatherClass TodayWeatherTest20 = new TodayWeatherClass("2018-12-11","인천",0);
        TodayWeatherTest20.run();
        assertEquals("인천 : 보통", TodayWeatherTest20.getResult());
    }
}
