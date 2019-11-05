package deu.java.team01.server.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateCheck {
    private int am;
    private int pm;
    private static final Logger logger = LoggerFactory.getLogger(DateCheck.class);

    public DateCheck(int key, int dateday) {
        if (key == 5) {
            switch (dateday) {
                case 0:
                    this.am = -1;
                    this.pm = 0;
                    break;
                case 1:
                    this.am = 1;
                    this.pm = 2;
                    break;
                case 2:
                    this.am = 3;
                    this.pm = 4;
                    break;
                default:
                    break;

            }
        }
        if (key == 6) {
            switch (dateday) {
                case 0:
                    this.am = 0;
                    this.pm = 1;
                    break;
                case 1:
                    this.am = 2;
                    this.pm = 3;
                    break;
                case 2:
                    this.am = 4;
                    this.pm = 5;
                    break;
                default:
                    break;
            }
        }
    }

    public String getPm() {
        String str;
        str = Integer.toString(pm);
        return str;
    }

    public String getAm() {
        String str;
        str = Integer.toString(am);
        return str;
    }
}
