package org.ac.cst8277.sun.guiquan.ums.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

@Data
@Entity
@Table(name = "users", schema = "ums", catalog = "ums")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "created", nullable = true)
    private Long created;

    @Column(name = "last_visit_id")
    private String last_visit_id;

    @Transient
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_has_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    Set<RoleEntity> roles;
}
