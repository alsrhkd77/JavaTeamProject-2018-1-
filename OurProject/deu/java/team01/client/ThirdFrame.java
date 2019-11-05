package deu.java.team01.client;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import deu.java.team01.library.punycode.PunycodeException;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThirdFrame{
    private JFrame ourThirdFrame;
    private JPanel posterPanel;
    private JPanel perfoInformPanel;
    private GridBagConstraints gridBagConstraints;
    private Desktop desktop;
    private JPanel weatherPanel;
    private JPanel weatherInfoPanel;
    private Client client;
    private JPanel misePanel;
    private JPanel amPanel;
    private JPanel pmPanel;
    private int index;
    private JPanel busPanel;
    private String performInfoString;
    private String weatherInfoString;
    private Date curSelected;
    private static final Logger logger = LoggerFactory.getLogger(ThirdFrame.class);

    /**
     * @return 창의 ID를 반환한다. 창을 지웠을 때 LinkedList에서 지울 때, 요청이 들어왔을 때 창의 ID를 불러내서 LinkedList를 순회할 때 사용한다.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param client 날씨 날짜 버튼을 클릭했을 때 서버로 정보를 전송하기 위해 client가 필요하다
     * @param index  창의 ID다 int타입이고 linkedList에 넣었을 때 창의 창을 구별하기 위해 필요하다
     */
    public ThirdFrame(Client client, int index) {
        logger.info("세번째 프레임 창이 생성되었다~");
        logger.info("이 아이의 ID는 {}이다", index);
        ourThirdFrame = new JFrame("상세 정보");
        amPanel = new JPanel();
        pmPanel = new JPanel();
        busPanel = new JPanel();
        busPanel.setBorder(BorderFactory.createTitledBorder("주변 버스 정류소"));
        weatherInfoPanel = new JPanel();
        weatherInfoPanel.setPreferredSize(new Dimension(400, 200));
        amPanel.setPreferredSize(new Dimension(130, 120));
        pmPanel.setPreferredSize(new Dimension(130, 120));
        busPanel.setPreferredSize(new Dimension(200, 80));
        misePanel = new JPanel();
        misePanel.setBorder(BorderFactory.createTitledBorder("미세먼지"));
        misePanel.setPreferredSize(new Dimension(260, 50));
        weatherInfoPanel.setLayout(new GridBagLayout());
        ourThirdFrame.getContentPane().setLayout(new GridBagLayout());
        perfoInformPanel = new JPanel();
        perfoInformPanel.setLayout(new GridBagLayout());
        posterPanel = new JPanel();
        performInfoString = "";
        weatherInfoString = "";
        amPanel.setBorder(BorderFactory.createTitledBorder("오전 날씨"));
        pmPanel.setBorder(BorderFactory.createTitledBorder("오후 날씨"));
        gridBagConstraints = new GridBagConstraints();
        weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridBagLayout());
        desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        this.client = client;
        this.index = index;
    }

    /**
     * @param weatherData 서버에서 보낸 날씨 정보
     * @brief 날씨 정보가 들어왔을 때 처리하기 위한 함수
     */
    public void weatherRequest(String weatherData) {
        logger.info("날씨 정보가 들어왔다~~");
        logger.info("날씨 정보를 열심히 넣기 시작한다~");
        weatherInfoString = weatherData;
        String[] parts = weatherData.split("\\n+");
        weatherInfoPanel.removeAll();
        misePanel.removeAll();
        amPanel.removeAll();
        pmPanel.removeAll();
        weatherInfoPanel.revalidate();
        weatherInfoPanel.repaint();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        if (parts.length == 12) {
            logger.info("들어온 날씨 정보는 오늘~2일 사이의 날씨 정보다~~");
            if (!parts[0].equals("")) {
                gridBagConstraints.gridwidth = 2;
                String temp = parts[0].split(":")[1];
                JLabel mise = new JLabel(temp);
                misePanel.add(mise, gridBagConstraints);
                weatherInfoPanel.add(misePanel, gridBagConstraints);
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.gridy++;
            }
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            JLabel amgangsupercent = new JLabel(parts[2]);
            amPanel.add(amgangsupercent, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel amgangsuHyeongTae = new JLabel(parts[3]);
            amPanel.add(amgangsuHyeongTae, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel amondo = new JLabel(parts[4]);
            amPanel.add(amondo, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel amweather = new JLabel(parts[5]);
            amPanel.add(amweather, gridBagConstraints);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            weatherInfoPanel.add(amPanel, gridBagConstraints);
            //여기까지 오전
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            JLabel pmgangsupercent = new JLabel(parts[7]);
            pmPanel.add(pmgangsupercent, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel pmgangsuHyeongTae = new JLabel(parts[8]);
            pmPanel.add(pmgangsuHyeongTae, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel pmondo = new JLabel(parts[9]);
            pmPanel.add(pmondo, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel pmweather = new JLabel(parts[10]);
            pmPanel.add(pmweather, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            weatherInfoPanel.add(pmPanel, gridBagConstraints);
            //TODO 날씨...... 아이콘....으로......
            //[1]오전[2]강수확률[3]강수형태[4]예상기온[5]날씨
            //[6]오후[7]강수확률[8]강수형태[9]예상기온[10]날씨
            //[11]창 번호
        }
        if (parts.length == 8) {
            logger.info("들어온 날씨 정보는 3일~8일사이의 날씨 정보다~~");

            JLabel amWeather = new JLabel(parts[1]);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            amPanel.add(amWeather, gridBagConstraints);
            JLabel pmWeather = new JLabel(parts[2]);
            pmPanel.add(pmWeather, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel minOndo = new JLabel(parts[4]);
            amPanel.add(minOndo, gridBagConstraints);
            JLabel maxOndo = new JLabel(parts[3]);
            pmPanel.add(maxOndo, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel amPromise = new JLabel(parts[5]);
            amPanel.add(amPromise, gridBagConstraints);
            JLabel pmPromise = new JLabel(parts[6]);
            pmPanel.add(pmPromise, gridBagConstraints);
            //[1] 오전 날씨
            //[2] 오후 날씨
            //[3] 최고 온도
            //[4] 최저 온도
            //[5] 오전 신뢰도
            //[6] 오후 신뢰도
            //[7] 창번호
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            weatherInfoPanel.add(amPanel, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            weatherInfoPanel.add(pmPanel, gridBagConstraints);
        }
        if (parts.length == 6) {
            logger.info("들어온 날씨 정보는 9일~10일사이의 날씨 정보다~~");
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            JLabel weather = new JLabel(parts[1]);
            weatherInfoPanel.add(weather, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel maxOndo = new JLabel(parts[2]);
            weatherInfoPanel.add(maxOndo, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel minOndo = new JLabel(parts[3]);
            weatherInfoPanel.add(minOndo, gridBagConstraints);
            gridBagConstraints.gridy++;
            JLabel promise = new JLabel(parts[4]);
            weatherInfoPanel.add(promise, gridBagConstraints);

            //[1]날씨
            //[2]최고온도
            //[3]최저온도
            //[4]신뢰도
            //[5]창번호
        }
        amPanel.revalidate();
        amPanel.repaint();
        pmPanel.revalidate();
        pmPanel.repaint();
        misePanel.revalidate();
        misePanel.repaint();
        weatherInfoPanel.revalidate();
        weatherInfoPanel.repaint();
    }

    /**
     * @brief 창을 완전히 초기화하는 함수이다. 창이 그냥 넘어가는 것처럼 보이게 하기 위해 창의 모든 것을 빼낸 후 다시 넣고 그리는 작업을 하기 위해 사용한다.
     */
    public void clear() {
        posterPanel.removeAll();
        busPanel.removeAll();
        perfoInformPanel.removeAll();
        weatherInfoPanel.removeAll();
        weatherPanel.removeAll();
        ourThirdFrame.getContentPane().removeAll();
        ourThirdFrame.getContentPane().revalidate();
        ourThirdFrame.getContentPane().repaint();
    }

    public void run(String data) {
        performInfoString = data;
        ourThirdFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("세번째 프레임의 ID가 {}인 아이가 LinkedList에서 삭제된다~", index);
                ourThirdFrame.dispose();
                client.thirdFrameDelete(index);
            }
        });
        String[] parts = data.split("\\n+");
        //parts[0] 상세정보
        //parts[1] 제목
        //parts[2] 시작일
        //parts[3] 종료일
        //parts[4] 공연 장소
        //parts[5] 장르
        //parts[6] 지역
        //parts[7] 가격
        //parts[8] 티켓 구매 링크 주소
        //parts[9] 연락처
        //parts[10] 포스터
        //parts[13] 공연장 링크 주소
        //parts[15] 구글맵스
        //parts[16] 정류소
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        String punyCodedHostName = null;
        String[] urlParts = parts[10].split("\\.");
        String imageurlString = null;
        Image image = null;
        URL url = null;
        if (urlParts[0].matches(".*[\u3131-\u314e\u314f-\u3163\uac00-\ud7a3]+.*")) {//한글이 있냐~~
            String hostName = urlParts[0].split("//")[1];
            logger.info("포스터 URL에 한글이 있다....");
            logger.info("한글 호스트 네임은 {}", hostName);
            try {
                punyCodedHostName = deu.java.team01.library.punycode.Punycode.encode(hostName);
                imageurlString = StringUtils.replace(parts[10], hostName, "xn--" + punyCodedHostName);
                logger.info("퓨니코딩된 url은 {}다", imageurlString);
            } catch (deu.java.team01.library.punycode.PunycodeException e) {
                logger.error("퓨니코딩 실패!!");
            }
        } else if (urlParts[1].matches(".*[\u3131-\u314e\u314f-\u3163\uac00-\ud7a3]+.*")) {
            try {
                String hostName = urlParts[1];
                logger.info("포스터 URL에 한글이 있다....");
                logger.info("한글 호스트 네임은 {}", hostName);
                punyCodedHostName = deu.java.team01.library.punycode.Punycode.encode(hostName);
                imageurlString = StringUtils.replace(parts[10], hostName, "xn--" + punyCodedHostName);
                logger.info("퓨니코딩된 url은 {}다", imageurlString);
            } catch (PunycodeException e) {
                logger.error("퓨니코딩 실패!!");
            }
        } else if (parts[10].matches(".*[\u3131-\u314e\u314f-\u3163\uac00-\ud7a3]+.*")) {
            logger.info("포스터 URL에 한글이 있다....");
            logger.info("원래 URL은 {}다", parts[10]);
            logger.info("호스트가 아닌 포스터 파일이 한글인 것 같다 유니코드로 인코딩을 시도해본다.");
            String postername = parts[10].split("/")[parts[10].split("/").length - 1];
            String korean = postername.split("\\.")[0];
            String encodedKorean = URLEncoder.encode(korean, StandardCharsets.UTF_8);
            imageurlString = StringUtils.replace(parts[10], korean, encodedKorean);
            logger.info("유니코드로 인코딩 된 url은 {}다", imageurlString);
        } else {
            imageurlString = parts[10];
            logger.info("URL에 한글이 없다~~");
            logger.info("포스터 URL은 {}다", parts[10]);
        }
        try {
            url = new URL(imageurlString);
            image = ImageIO.read(url);
        } catch (IllegalArgumentException ex) {
            logger.error("이미지 읽기 실패");
            logger.error("이미지 형태가 뭔가 많이 잘못됨 URL은 {}", imageurlString);
            image = null;
        } catch (MalformedURLException e) {
            logger.error("잘못된 형태의 URL {}", imageurlString);
        } catch (IOException e) {
            logger.error("이미지 읽는 중 IOException이 발생");
        }

        if (image != null) {

            posterPanel.setBorder(BorderFactory.createTitledBorder("포스터"));
            JLabel poster = new JLabel(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
            posterPanel.add(poster);
            gridBagConstraints.gridheight = 2;
            ourThirdFrame.getContentPane().add(posterPanel, gridBagConstraints);
            gridBagConstraints.gridheight = 1;
        }
        perfoInformPanel.setBorder(BorderFactory.createTitledBorder("공연 정보"));
        if (parts[1].contains("제목")) {

            JLabel titleText = new JLabel(parts[1]);
            perfoInformPanel.add(titleText, gridBagConstraints);
        }
        if (parts[2].contains("시작일")) {
            gridBagConstraints.gridy++;
            String[] startDateParts = parts[2].split(":");
            String startDate = startDateParts[1].trim();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy\ub144MM\uc6d4dd\uc77c");
            Date startDateTosdf = null;
            try {
                startDateTosdf = simpleDateFormat.parse(startDate);
            } catch (ParseException e) {
                logger.error("공연 날짜를 년월일로 바꿔 표기하기 위해 한 파싱이 실패...{}(시작일)", startDate);
            }
            String choolryuk = simpleDateFormat2.format(startDateTosdf);
            JLabel performStart = new JLabel("공연 시작일 : " + choolryuk);
            perfoInformPanel.add(performStart, gridBagConstraints);
        }
        if (parts[3].contains("종료일")) {
            gridBagConstraints.gridy++;
            String[] endDateParts = parts[3].split(":");
            String endDate = endDateParts[1].trim();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy\ub144MM\uc6d4dd\uc77c");
            Date startDateTosdf = null;
            try {
                startDateTosdf = simpleDateFormat.parse(endDate);
            } catch (ParseException e) {
                logger.error("공연 날짜를 년월일로 바꿔 표기하기 위해 한 파싱이 실패...{}(종료일)", endDate);
            }
            String choolryuk = simpleDateFormat2.format(startDateTosdf);
            JLabel performEnd = new JLabel("공연 종료일 : " + choolryuk);
            perfoInformPanel.add(performEnd, gridBagConstraints);
        }
        if (parts[4].contains("장소")) {
            gridBagConstraints.gridy++;
            JLabel performPlace = new JLabel(parts[4]);
            perfoInformPanel.add(performPlace, gridBagConstraints);
        }
        if (!parts[5].equals("")) {
            gridBagConstraints.gridy++;
            JLabel performjangleu = new JLabel(parts[5]);
            perfoInformPanel.add(performjangleu, gridBagConstraints);
        }
        if (!parts[6].equals("")) {
            gridBagConstraints.gridy++;
            JLabel performRegion = new JLabel(parts[6]);
            perfoInformPanel.add(performRegion, gridBagConstraints);
        }
        if (!parts[7].equals("")) {
            String[] temp = parts[7].split("/");
            StringBuilder tempBuilder = new StringBuilder("<html>");
            for (String obj : temp) {
                tempBuilder.append(obj);
                tempBuilder.append("<br />");
            }
            tempBuilder.append("</html>");
            gridBagConstraints.gridy++;
            JLabel price = new JLabel(tempBuilder.toString());
            perfoInformPanel.add(price, gridBagConstraints);
        }
        if (parts[8].matches("http://.*")) {
            gridBagConstraints.gridy++;
            JButton ticketing = new JButton("티켓 구매 링크");
            ticketing.setFocusPainted(false);
            ticketing.setContentAreaFilled(false);
            ticketing.setOpaque(false);
            ticketing.addActionListener(e -> {
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URL(parts[8]).toURI());
                    } catch (MalformedURLException e1) {
                        logger.error("잘못된 형태의 URL {}", parts[8]);
                    } catch (URISyntaxException e1) {
                        logger.error("URIException이 발생 {}", parts[8]);
                    } catch (IOException e1) {
                        logger.error("IOException이 발생");
                    }
                }
            });
            perfoInformPanel.add(ticketing, gridBagConstraints);
        }
        if (!parts[9].equals("")) {
            gridBagConstraints.gridy++;
            JLabel phoneNumber = new JLabel(parts[9]);
            perfoInformPanel.add(phoneNumber, gridBagConstraints);
        }

        if (parts[13].matches("http://.*")) {
            gridBagConstraints.gridy++;
            JButton coloseumLink = new JButton("공연장 링크");
            coloseumLink.setFocusPainted(false);
            coloseumLink.setContentAreaFilled(false);
            coloseumLink.setOpaque(false);
            coloseumLink.addActionListener(e -> {
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URL(parts[13]).toURI());
                    } catch (MalformedURLException e1) {
                        logger.error("잘못된 형태의 URL {}", parts[13]);
                    } catch (URISyntaxException e1) {
                        logger.error("URIException이 발생 {}", parts[13]);
                    } catch (IOException e1) {
                        logger.error("IOException이 발생");
                    }
                }
            });
            perfoInformPanel.add(coloseumLink, gridBagConstraints);
        }

        if (parts[15].matches("https://.*")) {
            gridBagConstraints.gridy++;
            JButton googleLink = new JButton("지도 링크(구글맵스)");
            googleLink.setFocusPainted(false);
            googleLink.setContentAreaFilled(false);
            googleLink.setOpaque(false);
            googleLink.addActionListener(e -> {
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URL(parts[15]).toURI());
                    } catch (MalformedURLException e1) {
                        logger.error("잘못된 형태의 URL {}", parts[15]);
                    } catch (URISyntaxException e1) {
                        logger.error("URIException이 발생 {}", parts[15]);
                    } catch (IOException e1) {
                        logger.error("IOException이 발생");
                    }
                }
            });
            perfoInformPanel.add(googleLink, gridBagConstraints);
            gridBagConstraints.gridy++;
        }
        if (!parts[16].equals("")) {
            String[] busStations = parts[16].split("-");
            StringBuilder stringBuilder = new StringBuilder("<html>");
            for (String obj : busStations) {
                stringBuilder.append(obj);
                stringBuilder.append("<br />");
            }
            stringBuilder.append("</html>");
            perfoInformPanel.add(busPanel, gridBagConstraints);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            busPanel.add(new JLabel(stringBuilder.toString()), gridBagConstraints);

        }
        int result = 0;
        int result2 = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date today = new Date();
            Date endDate = format.parse(parts[3].split(":")[1].trim());
            long calDate = endDate.getTime() - today.getTime();
            long calDateDay = calDate / (24 * 60 * 60 * 1000);
            result = (int) calDateDay;
        } catch (ParseException e) {
            logger.error("날짜 계산을 위해 한 Date파싱이 실패...{}", parts[3].split(":")[1].trim());
        }
        try {
            Calendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(Calendar.DATE, 10);
            Date startDate = format.parse(parts[2].split(":")[1].trim());
            long calDate = gregorianCalendar.getTime().getTime() - startDate.getTime();
            long calDateDay = calDate / (24 * 60 * 60 * 1000);
            result2 = (int) calDateDay;
        } catch (ParseException e) {
            logger.error("날짜 계산을 위해 한 Date파싱이 실패...{}", parts[2].split(":")[1].trim());
        }

        if (result <= -1) {
            //종료일이 과거니까 표시 안 함
            //To do nothing
        } else if (result2 < 0) {
            //시작일이 10일보다 크다
            //표시 안 한다.
        } else {

            JDateChooser weatherDateChooser = new JDateChooser();
            try {
                Date startDate = format.parse(parts[2].split(":")[1].trim());
                Date today = new Date();
                long calDate = startDate.getTime() - today.getTime();
                long calDateDay = calDate / (24 * 60 * 60 * 1000);
                if (calDateDay < 0) {
                    weatherDateChooser.setMinSelectableDate(today);
                    weatherDateChooser.setDate(today);
                    curSelected = today;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String temp = simpleDateFormat.format(today);
                    StringBuilder stringBuilder = new StringBuilder("창 번호 : " + index + "\n");
                    client.send(stringBuilder.toString() + "\n날씨 정보 요청\n" + parts[6] + "-" + temp);
                } else {
                    weatherDateChooser.setMinSelectableDate(startDate);
                    weatherDateChooser.setDate(startDate);
                    curSelected = startDate;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String temp = simpleDateFormat.format(startDate);
                    StringBuilder stringBuilder = new StringBuilder("창 번호 : " + index + "\n");
                    client.send(stringBuilder.toString() + "\n날씨 정보 요청\n" + parts[6] + "-" + temp);
                }
            } catch (ParseException e) {
                logger.error("날짜 계산을 위해 한 Date파싱이 실패...{}", parts[2].split(":")[1].trim());
            }
            try {
                Calendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.add(Calendar.DATE, 10);
                Date endDate = format.parse(parts[3].split(":")[1].trim());
                long calDate = endDate.getTime() - gregorianCalendar.getTime().getTime();
                long calDateDay = calDate / (24 * 60 * 60 * 1000);
                if (calDateDay > 0) {
                    weatherDateChooser.setMaxSelectableDate(gregorianCalendar.getTime());

                } else {
                    weatherDateChooser.setMaxSelectableDate(endDate);
                }
            } catch (ParseException e) {
                logger.error("날짜 계산을 위해 한 Date파싱이 실패...{}", parts[1].split(":")[1].trim());
            }

            JTextFieldDateEditor editor = (JTextFieldDateEditor) weatherDateChooser.getDateEditor();
            editor.setColumns(10);
            editor.setEditable(false);
            weatherDateChooser.addPropertyChangeListener(e -> {
                curSelected = weatherDateChooser.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(weatherDateChooser.getDate());
                StringBuilder stringBuilder = new StringBuilder("창 번호 : " + index + "\n");
                client.send(stringBuilder.toString() + "날씨 정보 요청\n" + parts[6] + "-" + date);
            });

            weatherInfoPanel.setBorder(BorderFactory.createTitledBorder("날씨 정보"));
            weatherPanel.setBorder(BorderFactory.createTitledBorder("날짜"));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            weatherPanel.add(weatherDateChooser, gridBagConstraints);
            gridBagConstraints.gridy = 1;
            weatherPanel.add(weatherInfoPanel, gridBagConstraints);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            ourThirdFrame.getContentPane().add(weatherPanel, gridBagConstraints);
        }
        JButton saveButton = new JButton("저장하기");
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        saveButton.addActionListener(e -> {
            FileSaver fileSaver = new FileSaver(parts[1]);
            fileSaver.save(performInfoString, weatherInfoString, curSelected);
        });
        ourThirdFrame.getContentPane().add(saveButton, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        ourThirdFrame.getContentPane().add(perfoInformPanel, gridBagConstraints);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        ourThirdFrame.setLocation((dimension.width/3)-(ourThirdFrame.getWidth()/2), 0);
        ourThirdFrame.pack();
        ourThirdFrame.setResizable(false);
        ourThirdFrame.setVisible(true);
    }
}
