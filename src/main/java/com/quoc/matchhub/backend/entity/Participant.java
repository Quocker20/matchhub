package com.quoc.matchhub.backend.entity;


import com.quoc.matchhub.backend.enums.ParticipantType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "participants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tournament_id", "reference_type", "reference_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    private ParticipantType referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "seed_number")
    private Integer seedNumber;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName; // Snapshot tên User hoặc tên Team

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;   // Snapshot ảnh đại diện

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
