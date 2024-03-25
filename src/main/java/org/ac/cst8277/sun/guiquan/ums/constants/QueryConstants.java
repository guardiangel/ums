package org.ac.cst8277.sun.guiquan.ums.constants;

public final class QueryConstants {


    private QueryConstants() {
    }
    public static final String GET_ALL_USERS =
            "SELECT s FROM UserEntity s";
    public static final String GET_TOKEN_BY_USERID = "SELECT ut FROM UserTokenEntity ut WHERE ut.userId=:userId";

    public static final String GET_USERTOKENENTITY_BY_TOKEN
            = "SELECT ut FROM UserTokenEntity ut WHERE ut.token=:token";

}
