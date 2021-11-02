package com.Oydin.Userservice.Repository;

import com.Oydin.Userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);

    User savaUser(User user);

//    List<User> getAllUsers(List<User> users);

//    List<User> getAllUsers();
}
