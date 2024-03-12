package org.ac.cst8277.sun.guiquan.ums.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
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

//    @JsonIgnore
//    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
//    Set<UserEntity> users = new HashSet<>();

}
