package todo.ui;

import bird.message.BirdMessageManager;
import bird.model.Bird;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FrameToDo extends JFrame {

    private int initialSize;
    private final JPanel listPanel;
    private final List<ToDoInputPanel> inputPanels = new ArrayList<>();
    private static final int MAX_TODO = 10;

    public FrameToDo(ToDoService toDoService, Bird bird, BirdMessageManager messageManager) {

        setTitle("오늘의 할 일 작성");
        setSize(768, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 배경 이미지
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/ToDo/memo.png")));
        Image bgImage = bgIcon.getImage();

        // 배경 패널 생성 및 paintComponent 오버라이드
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        // 리스트 영역
        listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(null);
        listPanel.setBounds(120, 180, 530, 380);
        backgroundPanel.add(listPanel);
        
        // inputPanels를 데이터베이스와 동기화
        List<ToDo> savedTodos = toDoService.findTodayToDo(bird.getUsername());
        initialSize = savedTodos.size();
        for(int i = 0; i < initialSize; ++i) {
            ToDo todo = savedTodos.get(i);
            ToDoInputPanel panel = new ToDoInputPanel(i + 1);
            panel.setTitle(todo.getTitle());
            panel.setContent(todo.getContent());
            inputPanels.add(panel);
        }
        // 빈 인풋 패널 하나 추가
        addNewToDoPanel();

        // 하단 버튼 영역
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        controlPanel.setOpaque(false);
        controlPanel.setBounds(120, 490, 530, 60);
        // 버튼 색상 지정
        JButton btnAdd = new JButton("할 일 추가");
        btnAdd.setBackground(new Color(116, 204, 116)); // 초록 계열(파스텔)
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setOpaque(true);
        btnAdd.setPreferredSize(new Dimension(110, 38));

        JButton btnSave = new JButton("할 일 등록");
        btnSave.setBackground(new Color(255, 128, 128)); // 연한 빨간색 계열
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setOpaque(true);
        btnSave.setPreferredSize(new Dimension(110, 38));

        controlPanel.add(btnAdd);
        controlPanel.add(btnSave);
        backgroundPanel.add(controlPanel);

        // ➕ 할 일 추가 버튼
        btnAdd.addActionListener(e -> {
            if (inputPanels.size() <= MAX_TODO) {
                ToDoInputPanel currentPanel = inputPanels.get(inputPanels.size() - 1);
                if(currentPanel.getTitle().isEmpty() | currentPanel.getContent().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "제목이나 내용이 비어있습니다!", "추가 실패", JOptionPane.ERROR_MESSAGE);
                } else {
                    addNewToDoPanel();
                }
            }
            if (inputPanels.size() > MAX_TODO) {
                JOptionPane.showMessageDialog(this, "ToDo 리스트에 더는 할 일을 추가할 수 없습니다.", "추가 실패", JOptionPane.INFORMATION_MESSAGE);
                btnAdd.setEnabled(false);
            }
        });

        // 💾 저장 버튼
        btnSave.addActionListener(e -> {
            String username = bird.getUsername();
            LocalDate today = LocalDate.now();
            int savedCount = 0;

            for (int i = initialSize; i < inputPanels.size(); ++i) {
                ToDoInputPanel panel = inputPanels.get(i);
                String title = panel.getTitle().trim();
                String content = panel.getContent().trim();
                if (!title.isEmpty() && !content.isEmpty()) {
                    ToDo todo = new ToDo(username, today, title, content, false);
                    boolean saved = toDoService.add(todo);
                    if (saved) savedCount++;
                }
            }

            if (savedCount > 0) {
                initialSize += savedCount;
                JOptionPane.showMessageDialog(this, savedCount + "개의 할 일이 등록되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
                messageManager.displayRandomMessage();
            }
            else {
                JOptionPane.showMessageDialog(this, "추가된 할 일이 없습니다.", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        });

        // 배경 패널을 컨텐트로 지정
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void addNewToDoPanel() {
        System.out.println(inputPanels.size());
        ToDoInputPanel lastPanel = !inputPanels.isEmpty() ? inputPanels.get(inputPanels.size() - 1) : null;
        ToDoInputPanel panel = new ToDoInputPanel(inputPanels.size() + 1);
        inputPanels.add(panel);
        panel.setBounds(20, 20, 490, 340);
        if(lastPanel != null)
            lastPanel.setVisible(false);
        listPanel.add(panel);
        listPanel.revalidate();
        listPanel.repaint();

    }

    // 내부 클래스: 할 일 입력 박스
    static class ToDoInputPanel extends JPanel {
        private final JTextField titleField;
        private final JTextArea contentArea;

        public ToDoInputPanel(int index) {
            setLayout(null);
            setOpaque(false);
            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEmptyBorder(),
                    "할 일 " + index,
                    0, 0,
                    new Font("맑은 고딕", Font.BOLD, 15),
                    new Color(180, 140, 0)
            ));

            // 제목 라벨
            JLabel lbl = new JLabel("제목: ");
            lbl.setFont(new Font("맑은 고딕", Font.BOLD, 15));
            lbl.setForeground(new Color(120, 100, 0));
            lbl.setBounds(30, 30, 60, 28);

            // 제목 입력창
            titleField = new JTextField();
            titleField.setBackground(new Color(255, 255, 255));
            titleField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            titleField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 170), 1, true));
            titleField.setBounds(90, 30, 340, 28);

            // 내용 입력창
            contentArea = new JTextArea();
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setBackground(new Color(255, 255, 255));
            contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            contentArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 170), 1, true));

            JScrollPane contentScroll = new JScrollPane(contentArea);
            contentScroll.setOpaque(false);
            contentScroll.getViewport().setOpaque(false);
            contentScroll.setBorder(BorderFactory.createEmptyBorder());
            contentScroll.setBounds(30, 80, 400, 200);

            add(lbl);
            add(titleField);
            add(contentScroll);
        }

        public String getTitle() {
            return titleField.getText();
        }

        public void setTitle(String title) {
            titleField.setText(title);
        }

        public String getContent() {
            return contentArea.getText();
        }

        public void setContent(String content) {
            contentArea.setText(content);
        }
    }
}
