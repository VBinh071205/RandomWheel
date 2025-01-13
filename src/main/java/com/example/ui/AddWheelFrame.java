package com.example.ui;

import com.example.dao.RewardDAO;
import com.example.dao.WheelDAO;
import com.example.model.Reward;
import com.example.model.Wheel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddWheelFrame extends JFrame {
    private JTextField nameField;
    private JPanel rewardPanel;
    private JButton addButton;
    private JButton saveButton;
    private List<JTextField> rewardFields;

    private WheelDAO wheelDAO;
    private RewardDAO rewardDAO;
    private LobbyFrame lobbyFrame;

    public AddWheelFrame(LobbyFrame lobbyFrame) {
        this.lobbyFrame = lobbyFrame;
        setTitle("Thêm Vòng Quay");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rewardFields = new ArrayList<>();
        wheelDAO = new WheelDAO();
        rewardDAO = new RewardDAO();
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.add(new JLabel("Tên vòng quay:"));
        nameField = new JTextField();
        formPanel.add(nameField);


        rewardPanel = new JPanel(new GridLayout(0, 1,10,10));
        JScrollPane scrollPane = new JScrollPane(rewardPanel);
        addButton = new JButton("Thêm Giải thưởng");

        addButton.addActionListener(e -> addRewardField());
        formPanel.add(addButton);



        saveButton = new JButton("Lưu Vòng Quay");
        saveButton.addActionListener(e -> saveWheel());


        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        add(mainPanel);
    }
    private void addRewardField() {

        if (rewardFields.size() >= 8){
            JOptionPane.showMessageDialog(this,"Số lượng giải thưởng không được quá 8","Thông báo",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField rewardField = new JTextField();
        rewardPanel.add(rewardField);
        rewardFields.add(rewardField);
        rewardPanel.revalidate();
        rewardPanel.repaint();
    }

    private void saveWheel() {
         String name = nameField.getText().trim();
         if (name.isEmpty()){
             JOptionPane.showMessageDialog(this,"Tên không được để trống","Thông báo",JOptionPane.ERROR_MESSAGE);
             return;
         }
         if (rewardFields.isEmpty()){
            JOptionPane.showMessageDialog(this,"Vui lòng thêm giải thưởng","Thông báo",JOptionPane.ERROR_MESSAGE);
            return;
         }
         List<Reward> rewards = new ArrayList<>();

         for (JTextField rewardField : rewardFields) {
             String rewardName = rewardField.getText().trim();
              if (rewardName.isEmpty()){
                  JOptionPane.showMessageDialog(this,"Tên giải thưởng không được để trống","Thông báo",JOptionPane.ERROR_MESSAGE);
                  return;
              }
              rewards.add(new Reward(rewardName));
         }
         Wheel wheel = new Wheel();
         wheel.setName(name);
         wheel.setCreateDate(new Date());
          int wheelId = wheelDAO.addWheel(wheel);
        if (wheelId > 0){
             for (Reward reward : rewards){
                  reward.setWheelId(wheelId);
                 rewardDAO.addReward(reward);
             }
             JOptionPane.showMessageDialog(this,"Lưu thành công");
             lobbyFrame.refreshList();
             dispose();
         }
    }
}