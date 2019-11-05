package deu.java.team01.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 남영우
 * @since 2018-11-23
 * @brief 두번째 화면 프레임이다
 */
public class SecondFrame{
    private JFrame ourSecondFrame;
    private JLabel searchResult;
    private JButton prevPage;
    private JButton nextPage;
    private JLabel titleText;
    private JLabel resultPage;
    private JLabel resultCount;
    private Client client;
    private GridBagConstraints gridBagConstraints;
    private int index;
    private static final Logger logger = LoggerFactory.getLogger(SecondFrame.class);

    /**
     *
     * @param client 버튼을 클릭했을 때 서버로 정보를 전송하기 위해 client가 필요하다
     * @param index 창의 ID다 int타입이고 linkedList에 넣었을 때 창의 창을 구별하기 위해 필요하다
     */
    public SecondFrame(Client client, int index) {
        logger.info("두번째 프레임이 생성됐다~");
        logger.info("이 두번째 프레임의 ID는 {}이다.",index);
        ourSecondFrame = new JFrame("검색 결과");
        ourSecondFrame.setLayout(new GridBagLayout());
        searchResult = new JLabel();
        prevPage = new JButton();
        nextPage = new JButton();
        titleText = new JLabel();
        resultPage = new JLabel();
        resultCount = new JLabel();
        gridBagConstraints = new GridBagConstraints();
        this.client = client;
        this.index = index;
    }

    /**
     *
     * @return 창의 ID를 반환한다. 창을 지웠을 때 LinkedList에서 지울 때, 요청이 들어왔을 때 창의 ID를 불러내서 LinkedList를 순회할 때 사용한다.
     */
    public int getIndex(){
        return index;
    }

    /**
     * @brief 창을 완전히 초기화하는 함수이다. 창이 그냥 넘어가는 것처럼 보이게 하기 위해 창의 모든 것을 빼낸 후 다시 넣고 그리는 작업을 하기 위해 사용한다.
     */
    public void clear() {
        ourSecondFrame.getContentPane().removeAll();
        ourSecondFrame.getContentPane().revalidate();
        ourSecondFrame.getContentPane().repaint();
    }

    /**
     *
     * @param message 서버에서 보낸 응답 메세지이다.
     * @brief 창에 필요한 것들을 넣고 button들에 ActionListener를 넣는 작업 모든 것을 한다.
     */
    public void run(String message) {
        logger.info("두번째 프레임이 창을 열심히 만들기 시작한다~");
        String[] parts = message.split("\\n+");
        StringBuilder[] makeButtonText = new StringBuilder[(parts.length - 9) / 7];
        String[] numbers = new String[(parts.length - 9) / 7];
        JButton[] pages = new JButton[0];

        int sangsu = (Integer.parseInt(parts[parts.length - 9]) - 1) / 4;

        if ((Integer.parseInt(parts[parts.length - 11]) - 1) / 4 > (Integer.parseInt(parts[parts.length - 9]) - 1) / 4) {
            pages = new JButton[4];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new JButton();
                pages[i].setText(String.valueOf(1 + i + sangsu * 4));

                if (pages[i].getText().equals(String.valueOf(parts[parts.length - 9]))) {
                    pages[i].setEnabled(false);
                    if (pages[i].getText().equals(String.valueOf("1"))) {
                        prevPage.setEnabled(false);
                    } else {
                        prevPage.setEnabled(true);
                    }
                    if (pages[i].getText().equals(parts[parts.length - 11])) {
                        nextPage.setEnabled(false);
                    } else {
                        nextPage.setEnabled(true);
                    }
                }


                int finalI = i;
                int finalI1 = i;
                pages[i].addActionListener(e -> {
                    logger.info("{} 페이지 번호를 눌렀다",1 + finalI1 + sangsu * 4);
                    StringBuilder builder = new StringBuilder(String.valueOf(1 + finalI1 + sangsu * 4));
                    builder.append("\n");
                    builder.append(parts[parts.length-7]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 6]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 5]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 4]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 3]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 2]);
                    builder.append("\n");
                    builder.append(index);
                    client.send(builder.toString());
                });
            }
        } else if ((Integer.parseInt(parts[parts.length - 11]) - 1) / 4 == (Integer.parseInt(parts[parts.length - 9]) - 1) / 4) {
            pages = new JButton[Integer.parseInt(parts[parts.length - 11]) % 4];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new JButton();
                pages[i].setText(String.valueOf(1 + i + sangsu * 4));
                if (pages[i].getText().equals(String.valueOf(parts[parts.length - 9]))) {
                    pages[i].setEnabled(false);
                    if (pages[i].getText().equals(String.valueOf("1"))) {//1이면 이전 페이지 못하게
                        prevPage.setEnabled(false);
                    } else {
                        prevPage.setEnabled(true);
                    }
                    if (pages[i].getText().equals(parts[parts.length - 11])) {//마지막 페이지면 다음 페이지 못하게
                        nextPage.setEnabled(false);
                    } else {
                        nextPage.setEnabled(true);
                    }
                }


                int finalI = i;
                pages[i].addActionListener(e -> {
                    logger.info("{} 페이지 번호를 눌렀다",1 + finalI + sangsu * 4);
                    StringBuilder builder = new StringBuilder(String.valueOf(1 + finalI + sangsu * 4));
                    builder.append("\n");
                    builder.append(parts[parts.length-7]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 6]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 5]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 4]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 3]);
                    builder.append("\n");
                    builder.append(parts[parts.length - 2]);
                    builder.append("\n");
                    builder.append(index);
                    client.send(builder.toString());
                });
            }
        }
        prevPage.setText("<");
        prevPage.addActionListener(e -> {
            logger.info("이전 페이지로 버튼을 눌렀다~");
            StringBuilder builder = new StringBuilder(String.valueOf(Integer.parseInt(parts[parts.length - 9]) - 1));
            builder.append("\n");
            builder.append(parts[parts.length-7]);
            builder.append("\n");
            builder.append(parts[parts.length - 6]);
            builder.append("\n");
            builder.append(parts[parts.length - 5]);
            builder.append("\n");
            builder.append(parts[parts.length - 4]);
            builder.append("\n");
            builder.append(parts[parts.length - 3]);
            builder.append("\n");
            builder.append(parts[parts.length - 2]);
            builder.append("\n");
            builder.append(index);
            client.send(builder.toString());
        });
        nextPage.setText(">");
        nextPage.addActionListener(e -> {
            logger.info("다음 페이지로 버튼을 눌렀다~");
            StringBuilder builder = new StringBuilder(String.valueOf(Integer.parseInt(parts[parts.length - 9]) + 1));
            builder.append("\n");
            builder.append(parts[parts.length-7]);
            builder.append("\n");
            builder.append(parts[parts.length - 6]);
            builder.append("\n");
            builder.append(parts[parts.length - 5]);
            builder.append("\n");
            builder.append(parts[parts.length - 4]);
            builder.append("\n");
            builder.append(parts[parts.length - 3]);
            builder.append("\n");
            builder.append(parts[parts.length - 2]);
            builder.append("\n");
            builder.append(index);
            client.send(builder.toString());
        });
        for (int i = 0; i < makeButtonText.length; i++) {
            makeButtonText[i] = new StringBuilder("<html>");
            for (int j = 0; j < 7; j++) {
                numbers[i] = parts[7 * i];
                if (j == 6) {
                    continue;
                }
                makeButtonText[i].append(parts[i * 7 + j + 1]);
                makeButtonText[i].append("<br />");
            }
            makeButtonText[i].append("</html>");
        }

        ourSecondFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("두번째 창 {}번 아이가 X를 눌러 창을 닫았다 이 아이는 이제 LinkedList에서 삭제될 거다~",index);
                ourSecondFrame.dispose();
                client.secondFrameDelete(index);
            }
        });

        searchResult.setText("총 페이지 수 : " + parts[parts.length - 11]);
        resultCount.setText("총 검색 결과 수 : " + parts[parts.length - 10]);
        titleText.setText("검색 결과");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        ourSecondFrame.getContentPane().add(titleText, gridBagConstraints);
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridy = 1;
        ourSecondFrame.getContentPane().add(searchResult, gridBagConstraints);
        gridBagConstraints.gridx = 12;
        ourSecondFrame.getContentPane().add(resultCount, gridBagConstraints);

        JButton[] results = new JButton[makeButtonText.length];

        for (int i = 0; i < makeButtonText.length; i++) {

            results[i] = new JButton(makeButtonText[i].toString());
            results[i].setPreferredSize(new Dimension(240, 125));
            results[i].setHorizontalAlignment(SwingConstants.LEFT);
            results[i].setFocusPainted(false);
            results[i].setContentAreaFilled(false);
            results[i].setOpaque(false);
            int finalI = i;
            results[i].addActionListener(e -> {
                logger.info("리스트에 있는 버튼을 클릭했다~");
                client.send("처음 요청\n"+"세번째페이지 요청 :" + numbers[finalI]);
            });
        }
        gridBagConstraints.anchor = 17;
        if (results.length == 1) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 16;
            ourSecondFrame.getContentPane().add(results[0], gridBagConstraints);
        } else if (results.length == 2) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 16;
            ourSecondFrame.getContentPane().add(results[0], gridBagConstraints);
            gridBagConstraints.gridy = 4;
            ourSecondFrame.getContentPane().add(results[1], gridBagConstraints);
        } else if (results.length == 3) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 16;
            ourSecondFrame.getContentPane().add(results[0], gridBagConstraints);
            gridBagConstraints.gridy = 4;
            ourSecondFrame.getContentPane().add(results[1], gridBagConstraints);
            gridBagConstraints.gridy = 6;
            ourSecondFrame.getContentPane().add(results[2], gridBagConstraints);
        } else if (results.length == 4) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 16;
            ourSecondFrame.getContentPane().add(results[0], gridBagConstraints);
            gridBagConstraints.gridy = 4;
            ourSecondFrame.getContentPane().add(results[1], gridBagConstraints);
            gridBagConstraints.gridy = 6;
            ourSecondFrame.getContentPane().add(results[2], gridBagConstraints);
            gridBagConstraints.gridy = 8;
            ourSecondFrame.getContentPane().add(results[3], gridBagConstraints);

        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        if (pages.length == 0) {

        } else if (pages.length == 1) {
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 10;
            ourSecondFrame.getContentPane().add(pages[0], gridBagConstraints);
        } else if (pages.length == 2) {
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 10;
            ourSecondFrame.getContentPane().add(pages[0], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            ourSecondFrame.getContentPane().add(pages[1], gridBagConstraints);
        } else if (pages.length == 3) {
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 10;
            ourSecondFrame.getContentPane().add(pages[0], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            ourSecondFrame.getContentPane().add(pages[1], gridBagConstraints);
            gridBagConstraints.gridx = 3;
            ourSecondFrame.getContentPane().add(pages[2], gridBagConstraints);
        } else if (pages.length == 4) {
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 10;
            ourSecondFrame.getContentPane().add(pages[0], gridBagConstraints);
            gridBagConstraints.gridx = 2;
            ourSecondFrame.getContentPane().add(pages[1], gridBagConstraints);
            gridBagConstraints.gridx = 3;
            ourSecondFrame.getContentPane().add(pages[2], gridBagConstraints);
            gridBagConstraints.gridx = 4;
            ourSecondFrame.getContentPane().add(pages[3], gridBagConstraints);
        }
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        ourSecondFrame.getContentPane().add(prevPage, gridBagConstraints);
        gridBagConstraints.gridx = 5;
        ourSecondFrame.getContentPane().add(nextPage, gridBagConstraints);
        ourSecondFrame.setResizable(false);
        ourSecondFrame.pack();
        ourSecondFrame.setVisible(true);
        if (numbers.length == 0) {
            noResult();
        }
    }
    private void noResult() {
        JOptionPane.showMessageDialog(null, "검색 결과가 없습니다", "검색 실패!", JOptionPane.INFORMATION_MESSAGE);
        ourSecondFrame.dispose();
        ourSecondFrame.setVisible(false);
        client.secondFrameDelete(index);

    }
}
