package org.ac.cst8277.sun.guiquan.ums.repositories;

import org.ac.cst8277.sun.guiquan.ums.entities.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<RoleEntity, String> {

}
