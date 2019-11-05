package deu.java.team01.server.dustozen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {


        DustOzen dustOzen = new DustOzen("2018-12-11", "서울", 0);
        dustOzen.run();

        logger.info(dustOzen.getResult());
        DustOzen testDust = new DustOzen("가나다라","1234",-1);
        testDust.run();
        logger.info(testDust.getResult());
        DustOzen testDust2 = new DustOzen("abcd","efg",0);
        testDust2.run();
        logger.info(testDust2.getResult());
        DustOzen testDust3 = new DustOzen("2018-12-04","가나다라마바사아자차카타파하",0);
        testDust3.run();
        logger.info(testDust3.getResult());
    }

}
