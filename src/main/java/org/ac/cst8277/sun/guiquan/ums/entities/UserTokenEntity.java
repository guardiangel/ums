package org.ac.cst8277.sun.guiquan.ums.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
