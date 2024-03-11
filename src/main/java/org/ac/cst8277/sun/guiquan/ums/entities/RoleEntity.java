package org.ac.cst8277.sun.guiquan.ums.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;


@Data
@Entity
@Table(name = "roles", schema = "ums", catalog = "ums")
public class RoleEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
//    Set<UserEntity> users;

}
