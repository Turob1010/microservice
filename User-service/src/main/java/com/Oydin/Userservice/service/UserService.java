package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Repository.UserRepository;
import com.Oydin.Userservice.VO.Product;
import com.Oydin.Userservice.VO.ResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {


    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    ResponseTemplateVO vo = new ResponseTemplateVO();

    public User createUser (User user){
        return userRepository.save(user);
    }

    public User getById(Integer userId){ return userRepository.getById(userId);
    }

    public List<User> getAll(){
       List<User> users = userRepository.findAll();
       return users;
    }


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


    public ResponseTemplateVO saveUserAndProduct(ResponseTemplateVO templateVO) {
        User user = templateVO.getUser();
        userRepository.save(user);
        Product newProduct = new Product(user.getProductId(), templateVO.getProduct().getProductName(), templateVO.getProduct().getPrice());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> requestBody = new HttpEntity<>(newProduct, headers);
        Product product = restTemplate.postForObject("http://localhoct:4084/products/saveproduct",
                requestBody,
                Product.class);

        return new ResponseTemplateVO(user ,product);
    }
    public ResponseTemplateVO getUserWithProduct(Integer userId){

        User user = userRepository.findByUserId(userId);
        Product product = restTemplate.getForObject("http://localhost:4084/products/" + user.getProductId(),Product.class);
        vo.setUser(user);
        vo.setProduct(product);
        return vo;
    }
        public ResponseTemplateVO  getAllUsersWithProducts(){
        List<User> users = userRepository.findAll();
       String GET_ALL_PRODUCT =  "http://localhost:4084/products/getall";
    Product[] list = restTemplate.getForObject(GET_ALL_PRODUCT, Product[].class);

    vo.setUser((User) users);


    return vo;
    }

    public ResponseTemplateVO updateUserAndProduct(ResponseTemplateVO vo){
        User updateUser = new User();
        Product updateInfo = new Product();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);


        // Data attached to the request.
        HttpEntity<Product> requestBody = new HttpEntity<>(updateInfo, headers);

        // Send request with PUT method.
        restTemplate.exchange("http://localhost:4084/products/update", HttpMethod.PUT, requestBody, Product.class);

        String resourceUrl = "http://localhost:4084/products/"  + updateInfo.getProductId();
        Product p = restTemplate.getForObject(resourceUrl, Product.class);

        vo.setProduct(p);
//        vo.setUser(update());
        return vo;
    }
    public ResponseTemplateVO deleteUserAndProduct(User user){
        Product product = new Product();
        userRepository.delete(user);
        String productD = "http://localhost:4084/products/" + user.getProductId();
        restTemplate.delete(productD);
        vo.setUser(user);
        vo.setProduct(vo.getProduct());

        return vo ;
    }



}
