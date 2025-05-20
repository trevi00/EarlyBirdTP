package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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
        table.setRowHeight(35); // 본문 행 높이 조절

        // 본문 셀 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 3) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // 본문 그리드선 색상 및 세로 그리드선 제거
        table.setShowGrid(true);
        table.setGridColor(new Color(0xBEE3F8));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);

        JTableHeader header = table.getTableHeader(); // 헤더 배경색
        header.setBackground(new Color(0xBEE3F8));
        header.setFont(new Font("맑은 고딕", Font.BOLD, 15)); // 헤더 Bold 폰트
        header.setPreferredSize(new Dimension(header.getWidth(), 42)); // 헤더 높이 조절
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setForeground(Color.BLACK);

        // 헤더 칼럼 사이 구분선 삭제
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(0xBEE3F8));
                c.setFont(new Font("맑은 고딕", Font.BOLD, 13));
                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createEmptyBorder());
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(0xFFF6D2)); // 스크롤 영역 색
        add(scrollPane, BorderLayout.CENTER);

        header.setFont(header.getFont().deriveFont(Font.BOLD)); // 헤더 폰트
        header.setForeground(Color.BLACK);

        // 저장 버튼
        JButton btnSave = new JButton("변경사항 저장");
        btnSave.setPreferredSize(new Dimension(0, 40));
        btnSave.setBackground(new Color(0x4364A5)); // 버튼 색
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);

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
            } else if(todo != null && todo.isDone() && !checked) {
                toDoService.markAsUndone(id);
                changedCount++;
            }
        }

        if (changedCount > 0) {
            messageManager.say("✅ " + changedCount + "개의 할 일을 수정했어요!");
        } else {
            messageManager.say("📝 변경된 할 일이 없습니다.");
        }

        loadToDos(); // 새로고침
    }
}
