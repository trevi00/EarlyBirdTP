package todo.ui;

import bird.message.BirdMessageManager;
import bird.model.Bird;
import bird.point.PointService;
import bird.service.BirdService;
import bird.ui.FrameBird;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FrameToDo extends JFrame {

    private final JPanel listPanel;
    private final List<ToDoInputPanel> inputPanels = new ArrayList<>();
    private static final int MAX_TODO = 10;

    public FrameToDo(ToDoService toDoService, Bird bird, BirdMessageManager messageManager,
                     BirdService birdService, PointService pointService) {

        setTitle("오늘의 할 일 작성");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 리스트 영역
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 처음 1개 추가
        addNewToDoPanel();

        // 하단 버튼 영역
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("할 일 추가");
        JButton btnSave = new JButton("할 일 등록");

        controlPanel.add(btnAdd);
        controlPanel.add(btnSave);
        add(controlPanel, BorderLayout.SOUTH);

        // ➕ 할 일 추가 버튼
        btnAdd.addActionListener(e -> {
            if (inputPanels.size() < MAX_TODO) {
                addNewToDoPanel();
            }
            if (inputPanels.size() == MAX_TODO) {
                btnAdd.setEnabled(false);
            }
        });

        // 💾 저장 버튼
        btnSave.addActionListener(e -> {
            String username = bird.getUsername();
            LocalDate today = LocalDate.now();
            int savedCount = 0;

            for (ToDoInputPanel panel : inputPanels) {
                String title = panel.getTitle().trim();
                String content = panel.getContent().trim();
                if (!title.isEmpty() && !content.isEmpty()) {
                    ToDo todo = new ToDo(username, today, title, content, false);
                    boolean saved = toDoService.add(todo);
                    if (saved) savedCount++;
                }
            }

            if (savedCount > 0) {
                JOptionPane.showMessageDialog(this, savedCount + "개의 할 일이 등록되었습니다.");
                messageManager.displayRandomMessage();
                new FrameBird(bird, birdService, messageManager, pointService).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "입력된 할 일이 없습니다.");
            }
        });

        setVisible(true);
    }

    private void addNewToDoPanel() {
        ToDoInputPanel panel = new ToDoInputPanel(inputPanels.size() + 1);
        inputPanels.add(panel);
        listPanel.add(panel);
        listPanel.revalidate();
        listPanel.repaint();
    }

    // 🔧 내부 클래스: 할 일 입력 박스
    static class ToDoInputPanel extends JPanel {
        private final JTextField titleField;
        private final JTextArea contentArea;

        public ToDoInputPanel(int index) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("할 일 " + index));

            titleField = new JTextField();
            contentArea = new JTextArea(3, 20);
            contentArea.setLineWrap(true);

            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.add(new JLabel("제목: "), BorderLayout.WEST);
            titlePanel.add(titleField, BorderLayout.CENTER);

            add(titlePanel, BorderLayout.NORTH);
            add(new JScrollPane(contentArea), BorderLayout.CENTER);
        }

        public String getTitle() {
            return titleField.getText();
        }

        public String getContent() {
            return contentArea.getText();
        }
    }
}
