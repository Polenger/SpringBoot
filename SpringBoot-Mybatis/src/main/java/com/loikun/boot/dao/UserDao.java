package com.loikun.boot.dao;

import com.loikun.boot.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User getUser(@Param("id") Long id);
}
