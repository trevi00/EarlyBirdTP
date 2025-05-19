package attendance.handler;

import attendance.service.AttendanceService;
import bird.message.BirdMessageManager;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.PointManager;
import bird.point.PointService;
import bird.service.BirdService;
import bird.ui.FrameBird;

import javax.swing.*;
import java.time.LocalDate;

/**
 * [AttendanceHandler]
 * - 출석 체크 + 포인트 적립 + 새 성장 관리 + 메시지 출력
 */
public class AttendanceHandler {

    private final AttendanceService attendanceService;
    private final Bird bird;
    private final BirdService birdService;
    private final BirdMessageProvider birdMessageProvider;
    private final FrameBird frameBird;
    private final BirdMessageManager messageManager;
    private PointService pointService;

    public AttendanceHandler(AttendanceService attendanceService,
                             Bird bird,
                             BirdService birdService,
                             BirdMessageProvider birdMessageProvider,
                             FrameBird frameBird,
                             BirdMessageManager messageManager,
                             PointService pointService) {
        this.attendanceService = attendanceService;
        this.bird = bird;
        this.birdService = birdService;
        this.birdMessageProvider = birdMessageProvider;
        this.frameBird = frameBird;
        this.messageManager = messageManager;
    }

    /**
     * 출석 체크를 수행한다.
     */
    public boolean handleAttendance(JFrame parentFrame, String username, LocalDate today) {
        boolean success = attendanceService.checkAttendance(username, today);

        if (success) {
            int hour = java.time.LocalTime.now().getHour();

            if (hour >= 4 && hour < 10) {
                messageManager.say("🎉 출석 완료! 얼리버드인 당신이 대단해요!");
            } else {
                messageManager.say("출석 완료! 다음엔 더 일찍 만나요 ☀️");
            }

            messageManager.speakRandom();
            return true;
        } else {
            JOptionPane.showMessageDialog(parentFrame, "이미 오늘 출석을 완료했습니다!");
            return false;
        }
    }

}
