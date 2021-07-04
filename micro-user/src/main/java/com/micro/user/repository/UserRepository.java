package com.micro.user.repository;

import com.micro.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByLoginName(String loginName);

    User findFirstByLoginNameAndPassword(String loginName, String password);
}
