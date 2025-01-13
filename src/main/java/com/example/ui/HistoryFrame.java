package com.example.ui;

import com.example.dao.HistoryDAO;
import com.example.model.History;
import com.example.model.Wheel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryFrame extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private HistoryDAO historyDAO;
    private Wheel wheel;


    public HistoryFrame(Wheel wheel) {
        this.wheel = wheel;
        setTitle("Lịch sử quay của vòng: " + wheel.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        historyDAO = new HistoryDAO();
        initUI();
        loadHistory();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Thời gian");
        tableModel.addColumn("Giải thưởng");

        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadHistory() {
        tableModel.setRowCount(0);
        List<History> histories = historyDAO.getHistoryByWheelId(wheel.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (History history : histories) {
            Object[] row = {sdf.format(history.getSpinTime()), history.getRewardName()};
            tableModel.addRow(row);
        }
    }
}