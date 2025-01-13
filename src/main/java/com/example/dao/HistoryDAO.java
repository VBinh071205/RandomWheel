package com.example.dao;

import com.example.database.DatabaseManager;
import com.example.model.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private DatabaseManager dbManager;

    public HistoryDAO() {
        dbManager = new DatabaseManager();
    }

    public void addHistory(History history) {
        String sql = "INSERT INTO History (spin_time, wheel_id,reward_name) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, history.getSpinTime());
            pstmt.setInt(2, history.getWheelId());
            pstmt.setString(3,history.getRewardName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<History> getHistoryByWheelId(int wheelId) {
        List<History> histories = new ArrayList<>();
        String sql = "SELECT * FROM History WHERE wheel_id = ? ORDER BY spin_time DESC";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, wheelId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    histories.add(mapResultSetToHistory(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }
    private History mapResultSetToHistory(ResultSet rs) throws SQLException {
         int id = rs.getInt("id");
        long spinTime = rs.getLong("spin_time");
        int wheelId = rs.getInt("wheel_id");
        String rewardName = rs.getString("reward_name");
        return new History(id, spinTime, wheelId,rewardName);
    }
}