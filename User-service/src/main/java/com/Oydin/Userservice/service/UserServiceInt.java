package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Exception.UserAlreadyExistsException;
import com.Oydin.Userservice.Exception.UserNotFoundException;

public interface UserServiceInt {

    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User userDetails,Integer userId) throws UserNotFoundException;
}
