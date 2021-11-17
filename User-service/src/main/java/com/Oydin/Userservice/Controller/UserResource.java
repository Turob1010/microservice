package com.Oydin.Userservice.Controller;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Exception.UserAlreadyExistsException;
import com.Oydin.Userservice.Exception.UserNotFoundException;
import com.Oydin.Userservice.VO.ResponseListTemplate;
import com.Oydin.Userservice.VO.ResponseTemplateVO;
import com.Oydin.Userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserResource
{

    @Autowired
    private UserService userService;


    @PostMapping("/createuserandproduct")
    public ResponseTemplateVO createUserAndProduct(@RequestBody ResponseTemplateVO templateVO){
        log.info("Inside createUserAndProduct method of UserResource");

        return userService.saveUserAndProduct(templateVO);
    }

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        log.info("Inside saveUser method of UserResource");
        User saveUser = userService.createUser(user);
        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
    }
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return new ResponseEntity("User already exists :", HttpStatus.CONFLICT);
    }

    @GetMapping("/{userId}")
    public ResponseTemplateVO getUserWithProduct(@PathVariable  Integer userId){
        log.info("Inside getUserWithProduct method of UserResource");
        return userService.getUserWithProduct(userId);
    }
    @GetMapping("/getall")
    public ResponseListTemplate getAllUsersWithProducts(){
        log.info("Inside getAll method of UserResource");
       return userService.getAllUsersWithProducts();
    }

    @GetMapping("/all")
    public List<User> getAll(){
        log.info("Inside getAll method of UserResource");
        return userService.getAll();
    }
    @PutMapping("/userupdate/{userId}")
    public User userUpdate(@RequestBody User userDetails ,@PathVariable Integer userId) throws UserNotFoundException {
        log.info("Inside userUpdate method of UserResource");
        User userUpdate = userService.updateUser(userDetails, userId);
        return userUpdate;
    }

    @PutMapping("/updateUserAndProduct")
    public ResponseTemplateVO updateUserAndProduct(@RequestBody ResponseTemplateVO templateVO){
        log.info("Inside updateUserAndProduct method of UserResource");
        return userService.updateUserAndProduct(templateVO);
    }


    @DeleteMapping("/delete/{userId}")
    public void delete (@PathVariable Integer userId){
        log.info("Inside delete method of UserResource");
        userService.deleteUser(userService.getById(userId));
    }


    @DeleteMapping("/deleteuserandproduct/{userId}")
        public ResponseEntity deleteUserAndProduct(@PathVariable Integer userId){
        log.info("Inside deleteUserAndProduct method of UserResource");
         userService.deleteUserAndProduct(userService.getUserWithProduct(userId));
           return   ResponseEntity.ok("delete");
    }

}
