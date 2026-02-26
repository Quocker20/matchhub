package com.quoc.matchhub.backend.enums;

public enum MatchStatus {
    SCHEDULED, // Đã lên lịch (nhưng chưa có đủ người)
    READY,     // Đã đủ 2 người, chờ đánh
    ONGOING,   // Đang thi đấu
    COMPLETED, // Đã đánh xong
    WALKOVER   // Bỏ cuộc/Thắng mặc định
}