package deu.java.team01.client;

import deu.java.team01.library.punycode.PunycodeException;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 남영우
 * @brief 파일 저장하기 버튼을 눌렀을 때 처리하기 위한 클래스
 * @since 2018-12-02
 */
public class FileSaver {
    private File textOut;
    private File folder;
    private String folderString;
    private static final Logger logger = LoggerFactory.getLogger(FileSaver.class);
    private static final String LINESEPARATOR = "line.separator";
    private static final String KOREAN = ".*[\u3131-\u314e\u314f-\u3163\uac00-\ud7a3]+.*";

    /**
     * @param title 공연의 제목, :으로 split한 후 trim 하고 폴더 명으로 사용한다
     */
    public FileSaver(String title) {
        logger.info("파일세이버 생성됨");
        String saveString;      //강선모: 폴더명 불가 처리를 위해 변수 선언
        saveString = title.split(":")[1].trim();
        if (saveString.contains("\\")) {
            saveString = StringUtils.replace(saveString, "\\", "");
        }
        if (saveString.contains("//")) {
            saveString = StringUtils.replace(saveString, "//", "");
        }
        if (saveString.contains(":")) {
            saveString = StringUtils.replace(saveString, ":", "");
        }
        if (saveString.contains("*")) {
            saveString = StringUtils.replace(saveString, "*", "");
        }
        if (saveString.contains("?")) {
            saveString = StringUtils.replace(saveString, "?", "");
        }
        if (saveString.contains("\"")) {
            saveString = StringUtils.replace(saveString, "\"", "");
        }
        if (saveString.contains("<")) {
            saveString = StringUtils.replace(saveString, "<", "");
        }
        if (saveString.contains(">")) {
            saveString = StringUtils.replace(saveString, ">", "");
        }
        if (saveString.contains("|")) {
            saveString = StringUtils.replace(saveString, "|", "");
        }
        folderString = saveString;

        logger.info("폴더명은 {}", folderString);
        folder = new File("./" + folderString);
        if (!folder.exists()) {
            folder.mkdir();
        }
        textOut = new File("./" + folderString + "//\uacf5\uc5f0\uc7a5\uc815\ubcf4.txt");

    }

    /**
     * @param performInfo 공연 정보 String
     * @param weatherInfo 날씨 정보 String
     * @param selected    현재 날씨 정보의 날짜의 Date형태다
     */
    public void save(String performInfo, String weatherInfo, Date selected) {
        String posterUrlParts = performInfo.split("\\n+")[10];
        String posterPath = posterUrlParts.split("/")[posterUrlParts.split("/").length - 1];
        String[] checkPuny = posterUrlParts.split("\\.");

        String punyCodedHost = null;
        String imageURLString = null;

        if (checkPuny[0].matches(KOREAN)) {
            String hostName = checkPuny[0].split("//")[1];
            logger.info("포스터 URL에 한글이 있다..... 한글 호스트 이름은 {}", hostName);
            logger.info("전체 URL은 {}", posterUrlParts);
            try {
                punyCodedHost = deu.java.team01.library.punycode.Punycode.encode(hostName);
                imageURLString = StringUtils.replace(posterUrlParts, hostName, "xn--" + punyCodedHost);
            } catch (PunycodeException e) {
                logger.error("퓨니코딩 실패");
            }


        } else if (checkPuny[1].matches(KOREAN)) {
            String hostName = checkPuny[1];
            logger.info("포스터 URL에 한글이 있다..... 한글 호스트 이름은 {}", hostName);
            try {
                punyCodedHost = deu.java.team01.library.punycode.Punycode.encode(hostName);
                imageURLString = StringUtils.replace(posterUrlParts, hostName, "xn--" + punyCodedHost);
            } catch (PunycodeException e) {
                logger.error("퓨니코딩 실패");
            }


        } else if (posterUrlParts.matches(KOREAN)) {
            logger.info("포스터 URL에 한글이 있다....");
            logger.info("원래 URL은 {}다", posterUrlParts);
            logger.info("호스트가 아닌 포스터 파일이 한글인 것 같다 유니코드로 인코딩을 시도해본다.");
            String postername = posterUrlParts.split("/")[posterUrlParts.split("/").length - 1];
            String korean = postername.split("\\.")[0];
            String encodedKorean = URLEncoder.encode(korean, StandardCharsets.UTF_8);
            imageURLString = StringUtils.replace(posterUrlParts, korean, encodedKorean);
        } else {
            logger.info("포스터 URL에 한글이 없다~");
            imageURLString = posterUrlParts;

        }
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("./" + folderString + "//" + posterPath))) {
            URL url = new URL(imageURLString);
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream())) {

                for (int i; (i = bufferedInputStream.read()) != -1; ) {
                    bufferedOutputStream.write(i);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("폴더 경로 찾기 실패");
        } catch (MalformedURLException e) {
            logger.error("잘못된 형태의 URL!! {}", posterUrlParts);
        } catch (IOException e) {
            logger.error("IOException이 발생!!");
        }


        try (FileWriter textFileWriter = new FileWriter(textOut)) {
            logger.info("이제 텍스트 파일 쓴다.");

            textFileWriter.write("공연 정보");
            textFileWriter.write(System.getProperty(LINESEPARATOR));
            String[] parts = performInfo.split("\\n+");
            for (int i = 0; i < parts.length - 2; i++) {
                textFileWriter.write(parts[i]);
                textFileWriter.write(System.getProperty(LINESEPARATOR));
            }
            String[] bus = parts[parts.length - 2].split("-");
            textFileWriter.write("주변 버스 정류장");
            textFileWriter.write(System.getProperty(LINESEPARATOR));
            for (String obj : bus) {
                textFileWriter.write(obj);
                textFileWriter.write(System.getProperty(LINESEPARATOR));
            }
            if (selected != null && !(weatherInfo.equals(""))) {
                textFileWriter.write("날씨 정보");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy\ub144MM\uc6d4dd\uc77c");
                String selectedDayString = simpleDateFormat.format(selected);
                textFileWriter.write(System.getProperty(LINESEPARATOR));
                textFileWriter.write(selectedDayString);
                textFileWriter.write(System.getProperty(LINESEPARATOR));
                String[] weatherParts = weatherInfo.split("\\n+");
                for (int i = 0; i < weatherParts.length - 2; i++) {
                    textFileWriter.write(weatherParts[i]);
                    textFileWriter.write(System.getProperty(LINESEPARATOR));
                }

            }

        } catch (IOException e) {
            logger.error("텍스트 파일 쓰기 실패다!!");
            JOptionPane.showMessageDialog(null, "파일 쓰기에 실패하였습니다...", "파일 쓰기 실패", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            logger.info("파일을 정상적으로 닫았다.");
            JOptionPane.showMessageDialog(null, "파일 쓰기에 성공하였습니다", "파일 쓰기 성공", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
