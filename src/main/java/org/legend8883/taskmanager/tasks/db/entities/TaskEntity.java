package org.legend8883.taskmanager.tasks.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.legend8883.taskmanager.tasks.db.enums.Importance;
import org.legend8883.taskmanager.tasks.db.enums.Status;
import org.legend8883.taskmanager.users.db.entities.UserEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;

    private String title;

    private String description;

    @Column(name = "date_time_when_you_need_to_complete")
    private LocalDateTime dateTimeWhenYouNeedToComplete;

    @Column(name = "time_to_complete_in_minutes")
    private Integer timeToCompleteInMinutes;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = Status.PLANNED;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
