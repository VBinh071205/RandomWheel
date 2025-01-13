package com.example.ui;

import com.example.model.Reward;
import com.example.model.Wheel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;


public class WheelInfoFrame extends JFrame {
    private Wheel wheel;
    private JLabel nameLabel;
    private JLabel createdDateLabel;

    private JTable rewardTable;
    private DefaultTableModel tableModel;

    public WheelInfoFrame(Wheel wheel) {
        this.wheel = wheel;
        setTitle("Thông tin vòng quay: " + wheel.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(2, 2,10,10));
        nameLabel = new JLabel("Tên: " + wheel.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        createdDateLabel = new JLabel("Ngày tạo: " + sdf.format(wheel.getCreateDate()));
        infoPanel.add(nameLabel);
        infoPanel.add(createdDateLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Tên Giải Thưởng");
        rewardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rewardTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadRewards();


        add(mainPanel);

    }
    private void loadRewards() {
        tableModel.setRowCount(0);

        List<Reward> rewards = wheel.getRewards();

        for (Reward reward : rewards) {
            Object[] row = {reward.getName()};
            tableModel.addRow(row);
        }
    }


}