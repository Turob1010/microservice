package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Repository.UserRepository;
import com.Oydin.Userservice.VO.Product;
import com.Oydin.Userservice.VO.ResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    public User createUser (User user){
        return userRepository.save(user);
    }
    public ResponseTemplateVO saveUserAndProduct(User user) {
        ResponseTemplateVO newVO = new ResponseTemplateVO();
        User user1 = userRepository.savaUser(user);
        Product newProduct = new Product(45, "Tv", 2500.0);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Product> requestBody = new HttpEntity<>(newProduct);
        ResponseEntity<Product> product
                = restTemplate.postForEntity("http://localhost:4084/saveproduct", requestBody, Product.class);
        Product product1 = product.getBody();
        newVO.setUser(user1);
        newVO.setProduct(product1);
        return newVO;
    }
    public User getById(Integer userId){ return userRepository.findById(userId).get();
    }

    public ResponseTemplateVO getUserWithProduct(Integer userId){
        ResponseTemplateVO vo  = new ResponseTemplateVO();

        User user = userRepository.findByUserId(userId);
        Product product = restTemplate.getForObject("http://localhost:4090/products/" + user.getProductId(),Product.class);
        vo.setUser(user);
        vo.setProduct(product);
        return vo;
    }
    public List<User> getAll(){
       List<User> users = userRepository.findAll();
       return users;
    }


//    public ResponseListTemplateVO getAllUsersWithProducts(){
//
//        List<User> users = userRepository.getAllUsers();
//       Product[]  products =  restTemplate.getForObject("http://localhost:4090/products/all",Product[].class);
//
//        return null;
//    }

    public User update (User user,Integer userId){
         userRepository.findById(userId).get();
        User user1 = userRepository.save(user);
        user1.setUserId(user.getUserId());
        user1.setUserName(user.getUserName());
        user1.setAge(user.getAge());
        user1.setProductId(user.getProductId());
        return user1;
    }

    public void  deleteUser(User user){
        userRepository.delete(user);


    }



}
