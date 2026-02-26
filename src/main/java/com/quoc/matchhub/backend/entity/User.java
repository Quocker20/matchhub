package com.quoc.matchhub.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Tự động ghi đè lệnh DELETE SQL tiêu chuẩn thành lệnh UPDATE
@SQLDelete(sql = "UPDATE users SET is_active = false WHERE id = ?")
// Tự động thêm điều kiện này vào MỌI câu lệnh SELECT
@SQLRestriction("is_active = true")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "is_system_admin")
    private boolean isSystemAdmin = false;

    // Cột dùng cho cơ chế Soft Delete
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // Audit log: Thời gian tạo
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Audit log: Thời gian cập nhật cuối cùng
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}