package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameToDoList extends JFrame {

    private final ToDoService toDoService;
    private final String username;
    private final BirdMessageManager messageManager;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final Map<Integer, String> rowIdMap = new HashMap<>(); // rowIndex -> todoId

    public FrameToDoList(ToDoService toDoService, String username, BirdMessageManager messageManager) {
        this.toDoService = toDoService;
        this.username = username;
        this.messageManager = messageManager;

        setTitle("할 일 목록 보기");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ✅ 테이블 설정
        String[] columnNames = {"날짜", "제목", "내용", "완료 여부"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 3) ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // 완료 여부만 수정 가능
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 저장 버튼
        JButton btnSave = new JButton("변경사항 저장");
        btnSave.addActionListener(e -> handleSave());
        add(btnSave, BorderLayout.SOUTH);

        loadToDos();

        setVisible(true);
    }

    private void loadToDos() {
        List<ToDo> list = toDoService.findByUsername(username);
        tableModel.setRowCount(0);  // 초기화
        rowIdMap.clear();


        int row = 0;
        for (ToDo todo : list) {
            tableModel.addRow(new Object[]{
                    todo.getDate().toString(),
                    todo.getTitle(),
                    todo.getContent(),
                    todo.isDone()
            });
            rowIdMap.put(row++, todo.getId()); // row index → id 저장
        }
    }

    private void handleSave() {
        int rowCount = tableModel.getRowCount();
        int changedCount = 0;

        for (int i = 0; i < rowCount; i++) {
            boolean checked = (Boolean) tableModel.getValueAt(i, 3);
            String id = rowIdMap.get(i);
            ToDo todo = toDoService.findById(id);

            if (todo != null && !todo.isDone() && checked) {
                toDoService.markAsDone(id);  // ✅ ID 기반 완료 처리 (포인트 2점 추가)
                changedCount++;
            }
        }

        if (changedCount > 0) {
            messageManager.say("✅ " + changedCount + "개의 할 일을 완료 처리했어요!");
        } else {
            messageManager.say("📝 변경된 할 일이 없습니다.");
        }

        loadToDos(); // 새로고침
    }
}
