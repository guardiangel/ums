package org.ac.cst8277.sun.guiquan.ums.requestvo;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserInputVo implements Serializable {

    private String id;

    private String name;

    private String email;

    private String password;

    private Long created;

    private Set<RoleInputVo> roles;

}
