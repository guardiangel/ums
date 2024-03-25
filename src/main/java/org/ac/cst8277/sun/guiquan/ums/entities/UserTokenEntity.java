package org.ac.cst8277.sun.guiquan.ums.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_token", schema = "ums", catalog = "ums")
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "token")
    private String token;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "issue_at")
    private Long issueAt;

    public UserTokenEntity(String userId, String token, Long duration, Long issueAt) {
        this.userId = userId;
        this.token = token;
        this.duration = duration;
        this.issueAt = issueAt;
    }

    @Transient
    private List<String> roleList = new ArrayList<>();
}
