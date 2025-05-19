package attendance.ui;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ImageLoadTest {

    @Test
    void checkImageIsLoadedSuccessfully() {
        // 이미지 리소스 로딩 (클래스패스 기준: /images/check.png)
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/check.png"));

        // 이미지 로드 상태 검사
        int loadStatus = icon.getImageLoadStatus();

        assertEquals(MediaTracker.COMPLETE, loadStatus, "❌ check.png 이미지 로드 실패");

        System.out.println("✅ check.png 이미지 로드 성공");
    }
}
