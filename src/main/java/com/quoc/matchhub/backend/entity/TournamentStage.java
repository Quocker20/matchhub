package com.quoc.matchhub.backend.entity;

import com.quoc.matchhub.backend.enums.StageFormat;
import com.quoc.matchhub.backend.enums.StageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tournament_stages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE tournament_stages SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class TournamentStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    //1: qualify_round   2:knockout round
    @Column(name = "stage_sequence", nullable = false)
    private Integer stageSequence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StageFormat format;

    //Chỉ dùng cho stage 1
    @Column(name = "target_qualifiers")
    private Integer targetQualifiers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StageStatus status = StageStatus.PENDING;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
