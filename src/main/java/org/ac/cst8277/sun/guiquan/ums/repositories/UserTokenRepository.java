package org.ac.cst8277.sun.guiquan.ums.repositories;

import org.ac.cst8277.sun.guiquan.ums.constants.QueryConstants;
import org.ac.cst8277.sun.guiquan.ums.entities.UserTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "userTokenRepository")
public interface UserTokenRepository extends CrudRepository<UserTokenEntity, String> {

    @Query(value = QueryConstants.GET_TOKEN_BY_USERID)
    UserTokenEntity getTokenById(String userId);

    @Query(value = QueryConstants.GET_USERTOKENENTITY_BY_TOKEN)
    UserTokenEntity getUserTokenEntityByToken(String token);

}
