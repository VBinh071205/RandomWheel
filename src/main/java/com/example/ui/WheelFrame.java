package com.example.ui;

import com.example.dao.HistoryDAO;
import com.example.model.History;
import com.example.model.Reward;
import com.example.model.Wheel;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class WheelFrame extends JFrame {
    private Wheel wheel;
    private JPanel wheelPanel;
    private JButton spinButton;
    private JButton historyButton;
    private int spinAngle = 0;
    private Timer spinTimer;
    private int selectedSlice;
    private HistoryDAO historyDAO;
    private double spinSpeed = 3;
    private boolean isSpinning = false;
    private static final int RANDOM_SPIN_DURATION = 2500;

    public WheelFrame(Wheel wheel) {
        this.wheel = wheel;
        historyDAO = new HistoryDAO();
        setTitle("Vòng quay: " + wheel.getName());
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        wheelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWheel(g);
            }
        };

        mainPanel.add(wheelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spinButton = new JButton("Quay");
        spinButton.addActionListener(e -> startSpin());

        historyButton = new JButton("Lịch sử");
        historyButton.addActionListener(e -> openHistoryFrame());

        buttonPanel.add(spinButton);
        buttonPanel.add(historyButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

   private void drawWheel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = Math.min(wheelPanel.getWidth(), wheelPanel.getHeight()) - 20;
        int x = (wheelPanel.getWidth() - width) / 2;
        int y = (wheelPanel.getHeight() - width) / 2;

        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, width);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(circle);

         List<Reward> rewards = wheel.getRewards();
        int sliceCount = rewards.size();
        double angleStep = 360.0 / sliceCount;

        // Vẽ các slice trước
          for (int i = 0; i < sliceCount; i++) {
            double startAngle = spinAngle + (sliceCount-1-i) * angleStep;
              double endAngle = startAngle + angleStep;
            Arc2D.Double arc = new Arc2D.Double(x, y, width, width, startAngle, angleStep, Arc2D.PIE);
             g2d.setColor(new Color(100 + (i * 100) % 155, 100 + (i * 50) % 155, 100 + (i * 30) % 155));
             g2d.fill(arc);
        }


        // Vẽ text lên trên slice
        for (int i = 0; i < sliceCount; i++) {
               double startAngle =  spinAngle + i * angleStep ;
               double endAngle = startAngle+angleStep;

           g2d.setColor(Color.BLACK);
           double textAngle = (startAngle + endAngle) / 2;
            double textX = x + width / 2 + Math.cos(Math.toRadians(textAngle)) * (width / 3.0);
           double textY = y + width / 2 + Math.sin(Math.toRadians(textAngle)) * (width / 3.0);
            String text = rewards.get(i).getName();

            Font font = new Font("Arial", Font.BOLD, 10);
           g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
             int textWidth = fm.stringWidth(text);
           int textHeight = fm.getHeight();
            g2d.translate(textX, textY);
           g2d.rotate(Math.toRadians(textAngle + 90));
          g2d.drawString(text, -textWidth / 2, textHeight/4);
          g2d.rotate(Math.toRadians(-(textAngle + 90)));
            g2d.translate(-textX, -textY);
        }


       // Vẽ mũi kim tam giác (trỏ xuống dưới)
         int centerX = x + width / 2;
        int centerY = y+20;
       int needleLength = width / 8;
      int triangleHeight = (int)(needleLength * Math.sqrt(3) /2);
      g2d.setColor(Color.RED);
       g2d.fillPolygon(new int[]{centerX - needleLength / 2, centerX + needleLength / 2, centerX}, new int[]{centerY - triangleHeight/2, centerY - triangleHeight/2, centerY + triangleHeight/2}, 3);
    }


    private void startSpin() {
        if (isSpinning) {
            return;
         }
        isSpinning = true;
        spinButton.setEnabled(false);
        spinAngle = 0;
        Random random = new Random();
         int randomSpinDuration = RANDOM_SPIN_DURATION + random.nextInt(2000);
        spinSpeed = 3;

        spinTimer = new Timer();

       spinTimer.scheduleAtFixedRate(new TimerTask() {
            long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

               if (currentTime - startTime >= randomSpinDuration) {
                  SwingUtilities.invokeLater(() -> {
                        stopSpin();
                    });
                    cancel();
                    return;
              }
                spinAngle += spinSpeed;
               spinAngle %= 360;
                wheelPanel.repaint();

            }
        }, 0, 16);
    }


     private void stopSpin() {
        spinTimer.cancel();
        spinTimer = null;
        isSpinning = false;
        int sliceCount = wheel.getRewards().size();
        double angleStep = 360.0 / sliceCount;

         double normalizedSpinAngle = (spinAngle % 360 + 360) % 360;
         selectedSlice = (int) Math.floor(normalizedSpinAngle / angleStep);

         if (selectedSlice >= sliceCount) {
             selectedSlice = 0;
        }

      Reward selectedReward = wheel.getRewards().get(selectedSlice);
         JOptionPane.showMessageDialog(this, "Bạn nhận được: " + selectedReward.getName());
         History history = new History();
       history.setRewardName(selectedReward.getName());
         history.setWheelId(wheel.getId());
         history.setSpinTime(System.currentTimeMillis());
          historyDAO.addHistory(history);

      spinButton.setEnabled(true);
   }

   private void openHistoryFrame() {
      HistoryFrame historyFrame = new HistoryFrame(wheel);
        historyFrame.setVisible(true);
    }
}