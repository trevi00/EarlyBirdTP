package app.ui;

import bird.message.BirdMessageDisplayer;

import javax.swing.*;

public class BannerDisplayer implements BirdMessageDisplayer {

    private final MessageBannerPanel panel;

    public BannerDisplayer(MessageBannerPanel panel) {
        this.panel = panel;
    }

    @Override
    public void showBanner(String message) {
        panel.showMessage(message);
    }

    @Override
    public void speak(String message) {
        JOptionPane.showMessageDialog(null, message, "🐤 마이 얼리버드", JOptionPane.INFORMATION_MESSAGE);
    }
}
