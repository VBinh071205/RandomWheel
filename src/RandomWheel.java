
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class RandomWheel {
    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<String> spinHistory = new ArrayList<>();
    private final int MAX_OPTIONS = 10;
    private final int MAX_HISTORY = 10;
    private final ArrayList<Color> wheelColors = new ArrayList<>();
    private boolean removeAfterSpin = false;

    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JCheckBox removeCheckBox;
    private JPanel wheelPanel;
    private int currentAngle = 0;

    public RandomWheel() {
        loadState(); // Tải trạng thái trước đó nếu có
        generateBrightColors(); // Sinh màu sáng
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Random Wheel v1.0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Bánh xe
        wheelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWheel(g);
                drawArrow(g); // Vẽ mũi kim chỉ
                drawHistory(g); // Vẽ lịch sử quay
            }
        };
        frame.add(wheelPanel, BorderLayout.CENTER);

        // Danh sách tùy chọn
        listModel = new DefaultListModel<>();
        options.forEach(listModel::addElement);
        JList<String> optionsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(optionsList);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Danh sách lựa chọn"), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Khu vực nhập và nút chức năng
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField optionInput = new JTextField(15);
        JButton addButton = new JButton("Thêm lựa chọn");
        JButton spinButton = new JButton("Quay"); // Đưa nút "Quay" vào cùng hàng
        JButton removeButton = new JButton("Xoá lựa chọn");

        addButton.addActionListener(e -> addOption(optionInput));
        optionInput.addActionListener(e -> addOption(optionInput)); // Thêm lựa chọn bằng phím Enter

        removeButton.addActionListener(e -> {
            int selectedIndex = optionsList.getSelectedIndex();
            if (selectedIndex != -1) {
                options.remove(selectedIndex);
                listModel.remove(selectedIndex);
                wheelPanel.repaint();
            }
        });

        spinButton.addActionListener(e -> spinWheel());

        removeCheckBox = new JCheckBox("Xoá kết quả sau khi quay");
        removeCheckBox.addActionListener(e -> removeAfterSpin = removeCheckBox.isSelected());

        inputPanel.add(optionInput);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(spinButton);
        inputPanel.add(removeCheckBox);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.add(listPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }

    private void addOption(JTextField optionInput) {
        String option = optionInput.getText().trim();
        if (option.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (options.size() >= MAX_OPTIONS) {
            JOptionPane.showMessageDialog(frame, "Đạt giới hạn lựa chọn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            options.add(option);
            listModel.addElement(option);
            optionInput.setText("");
            wheelPanel.repaint();
        }
    }

    private void spinWheel() {
        if (options.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Không có gì để quay", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Random random = new Random();
        int selected = random.nextInt(options.size());
        String result = options.get(selected);

        // Thêm hoạt ảnh quay
        Timer timer = new Timer(10, new ActionListener() {
            int steps = 0;
            int targetAngle = 360 * 9 + selected * (360 / options.size()); // Quay 9 vòng + dừng ở mục được chọn

            @Override
            public void actionPerformed(ActionEvent e) {
                currentAngle += 5;
                steps += 5;

                if (steps >= targetAngle) {
                    ((Timer) e.getSource()).stop();
                    // Hiển thị kết quả
                    JOptionPane.showMessageDialog(frame, "Xin chúc mừng đã quay trúng: " + result);

                    // Lưu vào lịch sử
                    if (spinHistory.size() >= MAX_HISTORY) {
                        spinHistory.remove(0);
                    }
                    spinHistory.add(result);

                    // Xóa tùy chọn nếu được chọn
                    if (removeAfterSpin) {
                        options.remove(selected);
                        listModel.remove(selected);
                    }

                    wheelPanel.repaint();
                } else {
                    wheelPanel.repaint();
                }
            }
        });
        timer.start();
    }

    private void drawWheel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = wheelPanel.getWidth();
        int height = wheelPanel.getHeight();
        int minDim = Math.min(width, height) - 50;

        if (options.isEmpty()) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval((width - minDim) / 2, (height - minDim) / 2, minDim, minDim);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Không lựa chọn", width / 2 - 30, height / 2);
            return;
        }

        int arcAngle = 360 / options.size();
        for (int i = 0; i < options.size(); i++) {
            Color segmentColor = wheelColors.get(i % wheelColors.size());
            g2d.setColor(segmentColor);
            g2d.fillArc((width - minDim) / 2, (height - minDim) / 2, minDim, minDim,
                    currentAngle + i * arcAngle, arcAngle);

            g2d.setColor(getContrastingColor(segmentColor));
            g2d.drawString(options.get(i), width / 2 - 30, height / 2 + 20 + i * 20); // Vẽ tên lựa chọn bên ngoài bánh xe
        }
    }

    private Color getContrastingColor(Color color) {
        int brightness = (int) (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114);
        return brightness > 128 ? Color.BLACK : Color.WHITE;
    }

    private void drawArrow(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        int width = wheelPanel.getWidth();
        int height = wheelPanel.getHeight();
        int minDim = Math.min(width, height) - 50;
        int centerX = width / 2;
        int centerY = height / 2;

        // Vẽ mũi kim bên ngoài vòng tròn
        int arrowLength = minDim / 2 + 20;
        int arrowWidth = 10;
        int[] xPoints = {centerX - arrowWidth, centerX, centerX + arrowWidth};
        int[] yPoints = {centerY - arrowLength, centerY - arrowLength - 20, centerY - arrowLength};

        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void generateBrightColors() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            wheelColors.add(new Color(200 + random.nextInt(56), 200 + random.nextInt(56), 200 + random.nextInt(56)));
        }
    }

    @SuppressWarnings("unused")
    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state.dat"))) {
            oos.writeObject(options);
            oos.writeObject(spinHistory);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Không thể lưu trạng thái", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("state.dat"))) {
            options = (ArrayList<String>) ois.readObject();
            spinHistory = (ArrayList<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Nếu không thể tải trạng thái (hoặc lần đầu chạy), không làm gì cả
        }
    }

    private void drawHistory(Graphics g) {
        // Hiển thị lịch sử quay
        if (!spinHistory.isEmpty()) {
            String historyText = "Lịch sử quay: " + String.join(", ", spinHistory);
            g.setColor(Color.BLACK);
            g.drawString(historyText, 10, 30);  // Vẽ ở vị trí góc trên trái
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandomWheel::new);
    }
}
