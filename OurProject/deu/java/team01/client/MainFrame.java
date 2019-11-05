package deu.java.team01.client;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFrame {
    private Client client;
    private JFrame ourMainFrame;
    private JLabel tild;
    private JLabel titleName;
    private JLabel period;
    private JLabel startPeriod;
    private JLabel endPeriod;
    private JLabel citylabel;
    private JLabel region;
    private JComboBox<String> city;
    private JLabel gugunlabel;
    private JComboBox<String> gugun;
    private JLabel jangleulabel;
    private JComboBox<String> jangleu;
    private JDateChooser startDate;
    private JDateChooser endDate;
    private GridBagConstraints gridBagConstraints;
    private JButton okButton;
    private String startDateString;
    private String endDateString;
    private static final String[] cities = {"전체", "서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "세종", "강원", "충남", "충북", "전남", "전북", "경남", "경북", "제주"};
    private static final String[] seoul = {"전체", "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"};
    private static final String[] busan = {"전체", "중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "강서구", "해운대구", "사하구", "금정구", "연제구", "수영구", "사상구", "기장군"};
    private static final String[] daegu = {"전체", "중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"};
    private static final String[] incheon = {"전체", "중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"};
    private static final String[] sejong = {"----"};//세종시는 하부 행정 구역이 없다
    private static final String[] gwangjoo = {"전체", "동구", "서구", "남구", "북구", "광산구"};
    private static final String[] daejeon = {"전체", "동구", "중구", "서구", "유성구", "대덕구"};
    private static final String[] ulsan = {"전체", "중구", "남구", "동구", "북구", "울주군"};
    private static final String[] kyunggi = {"전체", "수원", "성남", "안양", "안산", "용인", "광명", "평택", "과천", "오산", "시흥", "군포", "의왕", "하남", "이천", "안성", "김포", "화성", "광주", "여주", "부천", "양평", "고양", "의정부", "동두천", "구리", "남양주", "파주", "양주", "포천"};
    private static final String[] kangwon = {"전체", "춘천", "원주", "강릉", "동해", "태백", "속초", "삼척", "홍천", "횡성", "영월", "평창", "정선", "철원", "화천", "양구", "인제", "고성", "양양"};
    private static final String[] choongbuk = {"전체", "청주", "충주", "제천", "보은군", "옥천군", "영동군", "진천군", "괴산군", "음성군", "단양군", "증평군"};
    private static final String[] choongnam = {"전체", "천안", "공주", "보령", "아산", "서산", "논산", "계룡", "당진", "금산군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군"};
    private static final String[] jeonbuk = {"전체", "전주", "군산", "익산", "정읍", "남원", "김제", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군", "부안군"};
    private static final String[] jeonnam = {"전체", "목포", "여수", "순천", "나주", "광양", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"};
    private static final String[] kyungbuk = {"전체", "포항", "경주", "김천", "안동", "구미", "영주", "영천", "상주", "문경", "경산", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"};
    private static final String[] kyungnam = {"전체", "창원", "마산", "진해", "진주", "통영", "사천", "김해", "밀양", "거제", "양산", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"};
    private static final String[] jeju = {"----"};//하위 행정구역으로 시·군·구의 기초자치단체를 두지 않는 단층제 광역자치단체이다. 다만, 행정시의 형태로 제주시와 서귀포시가 있다.
    private static final String[] jangleulist = {"전체", "연극", "음악(콘서트, 뮤지컬)", "무용", "미술", "건축", "영상", "문학", "문화정책", "축제문화공간", "기타"};

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);


    public MainFrame(Client client) {


        logger.info("메인 프레임 객체가 생성되었다~~!");
        this.client = client;
        ourMainFrame = new JFrame("공연 검색!");
        tild = new JLabel();
        titleName = new JLabel();
        period = new JLabel();
        startPeriod = new JLabel();
        endPeriod = new JLabel();
        citylabel = new JLabel();
        city = new JComboBox<>();
        gugunlabel = new JLabel();
        gugun = new JComboBox<>();
        jangleulabel = new JLabel();
        jangleu = new JComboBox<>();
        startDate = new JDateChooser();
        endDate = new JDateChooser();
        region = new JLabel();
        gridBagConstraints = new GridBagConstraints();
        startDateString = "";
        endDateString = "";
        okButton = new JButton();
    }

    public void run() {
        logger.info("메인 프레임 객체가 돌아간다~!");
        ourMainFrame.getContentPane().setLayout(new GridBagLayout());
        titleName.setFont(new Font("궁서체", Font.BOLD, 15));
        titleName.setText("공연에 날씨에 오존까지 한번에 검색된다니 데단해~");
        ourMainFrame.setSize(800, 100);
        tild.setText("~");
        period.setText("기간");
        startPeriod.setText("공연 시작일");
        endPeriod.setText("공연 종료일");
        region.setText("지역");
        citylabel.setText("시/도");
        for (String obj : cities) {
            city.addItem(obj);
        }

        gugunlabel.setText("시/구/군");
        gugun.addItem("-시/도를 선택하세요-");
        jangleulabel.setText("장르");
        okButton.setText("검색!!");
        okButton.setPreferredSize(new Dimension(70, 20));

        for (String obj : jangleulist) {
            jangleu.addItem(obj);
        }
        JTextFieldDateEditor editor = (JTextFieldDateEditor) startDate.getDateEditor();
        editor.setColumns(10);
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) endDate.getDateEditor();
        editor2.setColumns(10);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        endDate.setMinSelectableDate(new Date());
        editor.setEditable(false);
        editor2.setEditable(false);
        startDate.setDate(new Date());
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        ourMainFrame.getContentPane().add(titleName, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 2;
        ourMainFrame.getContentPane().add(period, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        ourMainFrame.getContentPane().add(startPeriod, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        ourMainFrame.getContentPane().add(startDate, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        ourMainFrame.getContentPane().add(tild, gridBagConstraints);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        ourMainFrame.getContentPane().add(endPeriod, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        ourMainFrame.getContentPane().add(endDate, gridBagConstraints);
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        ourMainFrame.getContentPane().add(region, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        ourMainFrame.getContentPane().add(citylabel, gridBagConstraints);
        gridBagConstraints.gridx = 7;
        ourMainFrame.getContentPane().add(gugunlabel, gridBagConstraints);
        gridBagConstraints.gridx = 8;
        ourMainFrame.getContentPane().add(jangleulabel, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        ourMainFrame.getContentPane().add(jangleu, gridBagConstraints);
        gridBagConstraints.gridx = 7;
        ourMainFrame.getContentPane().add(gugun, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        ourMainFrame.getContentPane().add(city, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        ourMainFrame.getContentPane().add(okButton, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 10;
        ourMainFrame.getContentPane().add(new JLabel(" "), gridBagConstraints);
        ourMainFrame.setLocation(0, 0);
        ourMainFrame.setResizable(false);
        ourMainFrame.pack();
        ourMainFrame.setVisible(true);

        startDate.addPropertyChangeListener(
                e -> {
                    if ("date".equals(e.getPropertyName())) {

                        endDate.setMinSelectableDate(startDate.getDate());
                        logger.info("공연 시작 날짜를 바꿨다.");

                    }
                });
        endDate.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {

                startDate.setMaxSelectableDate(endDate.getDate());
                logger.info("공연 종료 날짜를 바꿨다.");

            }
        });
        ourMainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("메인 프레임의 X버튼을 눌렀다 종료한다~!");
                client.stopClient();
                System.exit(0);//창 오른쪽 상단 X버튼 눌렀을 경우 처리
            }
        });
        city.addActionListener(e -> {
            logger.info("도시를 선택했다");
            if (city.getSelectedItem().equals("서울")) {
                gugun.removeAllItems();
                for (String obj : seoul) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("부산")) {
                gugun.removeAllItems();
                for (String obj : busan) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("대구")) {
                gugun.removeAllItems();
                for (String obj : daegu) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("인천")) {
                gugun.removeAllItems();
                for (String obj : incheon) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("광주")) {
                gugun.removeAllItems();
                for (String obj : gwangjoo) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("대전")) {
                gugun.removeAllItems();
                for (String obj : daejeon) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("울산")) {
                gugun.removeAllItems();
                for (String obj : ulsan) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("경기")) {
                gugun.removeAllItems();
                for (String obj : kyunggi) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("세종")) {
                gugun.removeAllItems();
                for (String obj : sejong) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("강원")) {
                gugun.removeAllItems();
                for (String obj : kangwon) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("충북")) {
                gugun.removeAllItems();
                for (String obj : choongbuk) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("충남")) {
                gugun.removeAllItems();
                for (String obj : choongnam) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("전북")) {
                gugun.removeAllItems();
                for (String obj : jeonbuk) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("전남")) {
                gugun.removeAllItems();
                for (String obj : jeonnam) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("경북")) {
                gugun.removeAllItems();
                for (String obj : kyungbuk) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("경남")) {
                gugun.removeAllItems();
                for (String obj : kyungnam) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("제주")) {
                gugun.removeAllItems();
                for (String obj : jeju) {
                    gugun.addItem(obj);
                }
            }
            if (city.getSelectedItem().equals("전체")) {
                gugun.removeAllItems();
                gugun.addItem("전체");
                gugun.setSelectedItem("전체");
            }

        });

        okButton.addActionListener(e -> {
            logger.info("검색 버튼을 클릭했다! 요청할 메세지를 만들기 시작한다~");
            StringBuilder stringBuilder = new StringBuilder("");
            startDateString = dateFormat.format(startDate.getDate());
            if (endDate.getDate() != null) {
                endDateString = dateFormat.format(endDate.getDate());
            } else {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, 50);
                endDateString = dateFormat.format(cal.getTime());
            }

            stringBuilder.append("처음요청" + "\n");
            String sidoSelected = (String) city.getSelectedItem();
            String gugunSelected = (String) gugun.getSelectedItem();
            String jangleuSelected = (String) jangleu.getSelectedItem();
            stringBuilder.append(startDateString);
            stringBuilder.append("\n");
            if (endDateString.equals("")) {
                endDateString = "전체";

            }

            stringBuilder.append(endDateString);
            stringBuilder.append("\n");
            stringBuilder.append(sidoSelected);
            stringBuilder.append("\n");
            stringBuilder.append(gugunSelected);
            stringBuilder.append("\n");
            stringBuilder.append(jangleuSelected);
            stringBuilder.append("\n");
            stringBuilder.append("공연 리스트");
            stringBuilder.append("\n");
            stringBuilder.append("첫화면");
            logger.info("요청할 메세지는 {} 이다", stringBuilder.toString());
            client.send(stringBuilder.toString());


        });
        setCurLocationList();
    }

    private void setCurLocationList() {
        SearchLocal searchLocal = new SearchLocal();
        String[] curLocation = searchLocal.getLocal();
        city.setSelectedItem(curLocation[0]);
        gugun.setSelectedItem(curLocation[1]);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 10;
        if (!curLocation[0].equals("")) {
            if (curLocation[1].equals("없음")) {
                ourMainFrame.getContentPane().add(new JLabel("현재 위치는 " + curLocation[0] + "입니다."), gridBagConstraints);

            } else {
                ourMainFrame.getContentPane().add(new JLabel("현재 위치는 " + curLocation[0] + " " + curLocation[1] + "입니다."), gridBagConstraints);
            }
        }
        ourMainFrame.revalidate();
        ourMainFrame.repaint();
    }

    void unableToConnect(String ip, String port) {
        JOptionPane.showMessageDialog(null, ip + ":" + port + " 서버에 연결이 실패했습니다! 프로그램을 종료합니다", "연결 실패!", JOptionPane.INFORMATION_MESSAGE);
        ourMainFrame.dispose();
        ourMainFrame.setVisible(false);
    }
}
