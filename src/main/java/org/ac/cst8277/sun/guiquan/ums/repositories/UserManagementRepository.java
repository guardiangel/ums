package org.ac.cst8277.sun.guiquan.ums.repositories;

import org.ac.cst8277.sun.guiquan.ums.constants.QueryConstants;
import org.ac.cst8277.sun.guiquan.ums.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userManagementRepository")
public interface UserManagementRepository extends CrudRepository<UserEntity, String> {
    @Query(value = QueryConstants.GET_ALL_USERS, nativeQuery = false)
    List<UserEntity> getAllUser();

}
