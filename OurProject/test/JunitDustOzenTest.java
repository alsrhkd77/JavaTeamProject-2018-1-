package test;
import deu.java.team01.server.dustozen.DustOzen;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * @author 강선모
 * @brief DustOzen Class 에서 정상처리와 예외처리가 되는 지 20개의 메소드를 이용해 Test하는 클래스
 *         정상처리 10개, 완전히 잘못된 값 5개, 정규표현식이지만 잘못된 값 5개
 */
public class JunitDustOzenTest {
    @org.junit.Test
    public void test() {
                            // 정상작동
        DustOzen dustOzenTest = new DustOzen("2018-12-11","부산",0);
        dustOzenTest.run();
        assertEquals("부산 : 좋음", dustOzenTest.getResult());

        // 완전히 잘못된 값 5개  정규표현식이지만 잘못된값 5개, 나머지 정상 처리 10개
    }
    @org.junit.Test
    public void test2()	// 정상처리
    {
        DustOzen dustOzenTest2 = new DustOzen("2018-12-11","부산",1);
        dustOzenTest2.run();
        assertEquals("정보없음", dustOzenTest2.getResult());
    }
    @org.junit.Test
    public void test3()	// 정상처리
    {
        DustOzen dustOzenTest3 = new DustOzen("2018-12-11","제주",0);
        dustOzenTest3.run();
        assertEquals("제주 : 좋음", dustOzenTest3.getResult());
    }
    @org.junit.Test
    public void test4()	// 정상처리
    {
        DustOzen dustOzenTest4 = new DustOzen("2018-12-11","제주",1);
        dustOzenTest4.run();
        assertEquals("정보없음", dustOzenTest4.getResult());
    }
    @org.junit.Test
    public void test5()	// 정상처리
    {
        DustOzen dustOzenTest5 = new DustOzen("2018-12-11","전북",0);
        dustOzenTest5.run();
        assertEquals("전북 : 보통", dustOzenTest5.getResult());
    }
    @org.junit.Test
    public void test6()	// 정상 처리
    {
        DustOzen dustOzenTest6 = new DustOzen("2018-12-11","전북",0);
        dustOzenTest6.run();
        assertEquals("전북 : 보통", dustOzenTest6.getResult());
    }
    @org.junit.Test
    public void test7()	// 정상 처리
    {
        DustOzen dustOzenTest7 = new DustOzen("2018-12-11","광주",0);
        dustOzenTest7.run();
        assertEquals("광주 : 좋음", dustOzenTest7.getResult());
    }
    @org.junit.Test
    public void test8()	// 정상 처리
    {
        DustOzen dustOzenTest8 = new DustOzen("2018-12-11","서울",0);
        dustOzenTest8.run();
        assertEquals("서울 : 보통", dustOzenTest8.getResult());
    }
    @org.junit.Test
    public void test9()	// 정상 처리
    {
        DustOzen dustOzenTest9 = new DustOzen("2018-12-11","서울",1);
        dustOzenTest9.run();
        assertEquals("정보없음", dustOzenTest9.getResult());
    }
    @org.junit.Test
    public void test10()	// 완전히 다른 문자
    {
        DustOzen dustOzenTest10 = new DustOzen("2111-12-25","SEUOL",2);
        dustOzenTest10.run();
        assertEquals("잘못된 요청", dustOzenTest10.getResult());
    }
    @org.junit.Test
    public void test11()	// 완전히 다른 문자
    {
        DustOzen dustOzenTest11 = new DustOzen("2111-07-12","BUSAN",12);
        dustOzenTest11.run();
        assertEquals("잘못된 요청", dustOzenTest11.getResult());
    }
    @org.junit.Test
    public void test12(){	// 완전히 다른 문자
        DustOzen dustOzenTest12 = new DustOzen("2020-03-15","INCHEON",15);
        dustOzenTest12.run();
        assertEquals("잘못된 요청", dustOzenTest12.getResult());
    }
    @org.junit.Test
    public void test13()	// 완전히 다른 문자
    {
        DustOzen dustOzenTest13 = new DustOzen("2103-15-13","DEAGU",33);
        dustOzenTest13.run();
        assertEquals("잘못된 요청", dustOzenTest13.getResult());
    }
    @org.junit.Test
    public void test14()	// 완전히 다른 문자
    {
        DustOzen dustOzenTest14 = new DustOzen("2051-51-61","JEJU",15);
        dustOzenTest14.run();
        assertEquals("잘못된 요청", dustOzenTest14.getResult());
    }
    @org.junit.Test
    public void test15()	// 정규표현식인데 안되는 문자
    {
        DustOzen dustOzenTest15 = new DustOzen("2018-11-12","SEUOL",1);
        dustOzenTest15.run();
        assertEquals("잘못된 요청", dustOzenTest15.getResult());
    }
    @org.junit.Test
    public void test16()	// 정규표현식인데 안되는 문자
    {
        DustOzen dustOzenTest16 = new DustOzen("2018-12-11","서울",2);
        dustOzenTest16.run();
        assertEquals("잘못된 요청", dustOzenTest16.getResult());
    }
    @org.junit.Test
    public void test17()	// 정규표현식인데 안되는 문자 정규표현식에서 날짜가 31일까지지만 39일 넣어봄
    {
        DustOzen dustOzenTest17 = new DustOzen("2018-12-39","서울",1);
        dustOzenTest17.run();
        assertEquals("정보없음", dustOzenTest17.getResult());
    }
    @org.junit.Test
    public void test18()	// 정규표현식인데 안되는 문자
    {
        DustOzen dustOzenTest18 = new DustOzen("2018-06-26","광주",3);
        dustOzenTest18.run();
        assertEquals("잘못된 요청", dustOzenTest18.getResult());
    }
    @org.junit.Test
    public void test19()	// 정규표현식인데 안되는 문자
    {
        DustOzen dustOzenTest19 = new DustOzen("2017-11-11","인천",5);
        dustOzenTest19.run();
        assertEquals("잘못된 요청", dustOzenTest19.getResult());
    }
    @org.junit.Test
    public void test20()	// 정상작동
    {
        DustOzen dustOzenTest20 = new DustOzen("2018-12-11","인천",0);
        dustOzenTest20.run();
        assertEquals("인천 : 보통", dustOzenTest20.getResult());
    }
}