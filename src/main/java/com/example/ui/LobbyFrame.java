package com.example.ui;

import com.example.dao.WheelDAO;
import com.example.model.Wheel;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class LobbyFrame extends JFrame {
    private JList<Wheel> wheelList;
    private DefaultListModel<Wheel> listModel;
    private JButton addButton;
    private WheelDAO wheelDAO;


    public LobbyFrame() {
        setTitle("Vòng Quay May Mắn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        wheelDAO = new WheelDAO();
        initUI();
        loadWheels();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        wheelList = new JList<>(listModel);
        wheelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(wheelList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Thêm Vòng Quay");
        addButton.addActionListener(e -> openAddWheelFrame());
        buttonPanel.add(addButton);


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        wheelList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 1) {

                    // Get the item that was double-clicked
                    int index = list.locationToIndex(evt.getPoint());
                    Wheel wheel = (Wheel) list.getModel().getElementAt(index);

                    // Open a pop-up menu
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem spinItem = new JMenuItem("Quay");
                    spinItem.addActionListener(e -> openWheelFrame(wheel));
                    popup.add(spinItem);
                    JMenuItem infoItem = new JMenuItem("Xem thông tin");
                    infoItem.addActionListener(e -> openWheelInfoFrame(wheel));
                    popup.add(infoItem);

                    popup.show(list, evt.getX(), evt.getY());
                }
            }
        });


        add(mainPanel);
    }

    private void loadWheels() {
        listModel.clear();
        List<Wheel> wheels = wheelDAO.getAllWheels();
        for (Wheel wheel : wheels) {
            listModel.addElement(wheel);
        }
    }


    private void openAddWheelFrame() {
        AddWheelFrame addWheelFrame = new AddWheelFrame(this);
        addWheelFrame.setVisible(true);
    }
    public void refreshList(){
       loadWheels();
    }

    private void openWheelFrame(Wheel wheel) {
        WheelFrame wheelFrame = new WheelFrame(wheel);
        wheelFrame.setVisible(true);
    }

    private void openWheelInfoFrame(Wheel wheel) {
        WheelInfoFrame wheelInfoFrame = new WheelInfoFrame(wheel);
        wheelInfoFrame.setVisible(true);
    }
}