package com.quoc.matchhub.backend.entity;

import com.quoc.matchhub.backend.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Trận đấu này thuộc Giai đoạn nào? (Stage 1 hay Stage 2)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private TournamentStage stage;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    // Mã trận đấu để Frontend vẽ Bracket (VD: W1, L2, Final)
    @Column(name = "match_identifier", length = 50)
    private String matchIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant1_id")
    private Participant participant1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant2_id")
    private Participant participant2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Participant winner;

    // ==========================================
    // 1. ADJACENCY LIST (Tự tham chiếu để vẽ Cây)
    // ==========================================
    // Trận tiếp theo nếu Thắng (Đi tiếp lên nút cha)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_match_id")
    private Match nextMatch;

    // Trận tiếp theo nếu Thua (Dùng cho nhánh Double Elimination)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_loser_match_id")
    private Match nextLoserMatch;


    // ==========================================
    // 2. POLYMORPHISM DATA (Dữ liệu đa hình JSON)
    // ==========================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "match_details", columnDefinition = "json")
    private Map<String, Object> matchDetails;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status = MatchStatus.SCHEDULED;

    // Thời gian bắt đầu dự kiến
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    // Thời gian kết thúc dự kiến (Dùng để Auto-Scheduling tính toán Slot trống)
    @Column(name = "estimated_end_time")
    private LocalDateTime estimatedEndTime;

    // Thời gian kết thúc thực tế (Khi trọng tài bấm nút "Hoàn thành")
    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}