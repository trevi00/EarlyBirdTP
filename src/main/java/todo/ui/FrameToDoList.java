package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
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

        getContentPane().setBackground(new Color(0xFFF6D2)); // 여백 포함 전체 배경 색

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
        table.setBackground(new Color(0xFFF6D2)); // 테이블 배경색
        table.setGridColor(new Color(237, 141, 141)); // 그리드 선 색
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JTableHeader header = table.getTableHeader(); // 헤더 배경색
        header.setBackground(new Color(0xBEE3F8));
        header.setFont(new Font("맑은 고딕", Font.BOLD, 13)); // 헤더 Bold 폰트
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(237, 141, 141)));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(0xFFF6D2)); // 스크롤 영역 색
        add(scrollPane, BorderLayout.CENTER);

        header.setFont(header.getFont().deriveFont(Font.BOLD)); // 헤더 폰트
        header.setForeground(Color.BLACK);

        // 저장 버튼
        JButton btnSave = new JButton("변경사항 저장");

        btnSave.setBackground(new Color(116, 204, 116)); // 버튼 색
        btnSave.setForeground(Color.WHITE);
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);

        btnSave.addActionListener(e -> handleSave());
        add(btnSave, BorderLayout.SOUTH);

        loadToDos();

        setVisible(true);
    }

    private void loadToDos() {
        List<ToDo> list = toDoService.findByUsername(username);
        list.sort((a, b) -> a.getId().compareTo(b.getId()));
        tableModel.setRowCount(0);  // 초기화
        rowIdMap.clear();

        LocalDate today = LocalDate.now();

        int row = 0;
        for (ToDo todo : list) {
            if(!todo.getDate().equals(today)) continue; //
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
            messageManager.say(changedCount + "개의 할 일을 완료 처리했어요!");
        } else {
            messageManager.say("변경된 할 일이 없습니다.");
        }

        loadToDos(); // 새로고침
    }
}
