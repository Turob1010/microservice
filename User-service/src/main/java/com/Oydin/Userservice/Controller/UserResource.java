package com.Oydin.Userservice.Controller;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.VO.ResponseListTemplateVO;
import com.Oydin.Userservice.VO.ResponseTemplateVO;
import com.Oydin.Userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserResource
{
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser (@RequestBody User user){
        log.info("Inside saveUser method of UserResource");
        return userService.createUser(user);
    }
    @PostMapping("/createuserandproduct")
    public ResponseTemplateVO createUserAndProduct(@RequestBody User user){
        log.info("Inside createUserAndProduct method of UserResource");
        return userService.saveUserAndProduct(user);
    }
    @GetMapping("/{userId}")
    public ResponseTemplateVO getUserWithProduct(@PathVariable Integer userId){
        log.info("Inside getUserWithProduct method of UserResource");
        return userService.getUserWithProduct(userId);
    }
//    @GetMapping("/getall")
//    public ResponseListTemplateVO getAllUsersWithProducts(){
//        log.info("Inside getAll method of UserResource");
//        return userService.getAllUsersWithProducts();
//    }

    @GetMapping("/all")
    public List<User> getAll(){
        log.info("Inside getAll method of UserResource");
        return userService.getAll();
    }

    @PutMapping("/update/{userId}")
    public User     userUpdate (@RequestBody User user,@PathVariable Integer userId){
        log.info("Inside userUpdate method of UserResource");
        User user1 = userService.update (user,userId);
        return user1;
    }
    @DeleteMapping("/delete/{userId}")
            public void delete (@PathVariable Integer userId){
        log.info("Inside delete method of UserResource");
        userService.deleteUser(userService.getById(userId));
    }

}
