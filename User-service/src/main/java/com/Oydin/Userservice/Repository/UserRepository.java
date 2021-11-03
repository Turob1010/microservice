package com.Oydin.Userservice.Repository;

import com.Oydin.Userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;



@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);
    User deleteUser(User user);

}
