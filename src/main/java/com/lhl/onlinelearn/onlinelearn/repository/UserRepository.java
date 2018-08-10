package com.lhl.onlinelearn.onlinelearn.repository;

import com.lhl.onlinelearn.onlinelearn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findUserByUserName(String userName);
}
