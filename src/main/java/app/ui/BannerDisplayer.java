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
        JLabel centeredLabel = new JLabel(message, SwingConstants.CENTER);
        JOptionPane.showMessageDialog(null, centeredLabel, "🐤 새가 말해요!", JOptionPane.PLAIN_MESSAGE);
    }
}