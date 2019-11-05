package deu.java.team01.server.performance;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDrive {
    private static final Logger logger = LoggerFactory.getLogger(TestDrive.class);
    public static void main(String[] args){
        RequestInfo temp1 = new RequestInfo("18816");


        //RequestSearchPeriod temp2 = new RequestSearchPeriod("","20180101", 1);


        //RequestSearchArea temp3 = new RequestSearchArea("부산", "해운대구", 1);


        /*RequestSearchGenre temp4 = new RequestSearchGenre("20110101","20600101","경남","창원","",1);
        StringBuilder[] results = temp4.getResultBuilder();
        for(StringBuilder obj : results){
            System.out.println(obj.toString());
        }*/
        //System.out.println(temp1.getResult());

    }
}
