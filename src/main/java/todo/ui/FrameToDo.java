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

public class FrameToDo extends JFrame {

    private final JTextArea[] textAreas = new JTextArea[3];
    private final BirdService birdService;
    private final PointService pointService;

    public FrameToDo(ToDoService toDoService, Bird bird, BirdMessageManager birdMessageManager,
                     BirdService birdService, PointService pointService) {
        this.birdService = birdService;
        this.pointService = pointService;

        setTitle("오늘의 할 일 작성");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 3; i++) {
            textAreas[i] = new JTextArea();
            textAreas[i].setLineWrap(true);
            textAreas[i].setBorder(BorderFactory.createTitledBorder("할 일 " + (i + 1)));
            inputPanel.add(new JScrollPane(textAreas[i]));
        }

        JButton btnSave = new JButton("할 일 등록");
        btnSave.addActionListener(e -> {
            String username = bird.getUsername();
            LocalDate today = LocalDate.now();
            int savedCount = 0;

            for (JTextArea area : textAreas) {
                String content = area.getText().trim();
                if (!content.isEmpty()) {
                    ToDo todo = new ToDo(username, today, content, content, false);
                    boolean saved = toDoService.add(todo);
                    if (saved) savedCount++;
                }
            }

            if (savedCount > 0) {
                JOptionPane.showMessageDialog(this, savedCount + "개의 할 일이 등록되었습니다.");
                birdMessageManager.displayRandomMessage();

                new FrameBird(bird, birdService, birdMessageManager, pointService).setVisible(true);

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "입력된 할 일이 없습니다.");
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(btnSave, BorderLayout.SOUTH);

        setVisible(true);
    }
}
