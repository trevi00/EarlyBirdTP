package attendance.handler;

import javax.swing.*;

public class TestAttendanceHandler {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummyFrame = new JFrame("테스트 프레임");
            dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummyFrame.setSize(350, 350);
            dummyFrame.setLocationRelativeTo(null);
            dummyFrame.setVisible(true);

            // 얼리버드 출석 메시지 테스트
            //AttendanceHandlerDesign.showAttendanceMessage(dummyFrame, 1);

            // 일반 출석 메시지 테스트 (필요시 주석 해제)
             AttendanceHandlerDesign.showAttendanceMessage(dummyFrame, 2);
        });
    }
}