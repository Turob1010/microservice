package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Repository.UserRepository;
import com.Oydin.Userservice.VO.Product;
import com.Oydin.Userservice.VO.ResponseListTemplate;
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
    ResponseListTemplate voList = new ResponseListTemplate();

    public User createUser (User user){
        return userRepository.save(user);
    }

    public User getById(Integer userId){ return userRepository.findById(userId).get();
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
        Product product = restTemplate.postForObject("http://PRODUCT-SERVICE/products/saveproduct",
                requestBody,
                Product.class);

        return new ResponseTemplateVO(user ,product);
    }
    public ResponseTemplateVO getUserWithProduct(Integer userId){

        User user = userRepository.findByUserId(userId);
        Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + user.getProductId(),Product.class);
        vo.setUser(user);
        vo.setProduct(product);
        return vo;
    }
        public ResponseListTemplate  getAllUsersWithProducts(){
        List<User> users = userRepository.findAll();
       String GET_ALL_PRODUCT =  "http://PRODUCT-SERVICE/products/getall";
    Product[] products = restTemplate.getForObject(GET_ALL_PRODUCT, Product[].class);
    voList.setUsers(users);
    voList.setProducts(List.of(products));


    return voList;
    }

    public ResponseTemplateVO updateUserAndProduct(ResponseTemplateVO templateVO){
        Product updateInfo = new Product();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Product> requestBody = new HttpEntity<>(updateInfo, headers);

        restTemplate.exchange("http://PRODUCT-SERVICE/products/update", HttpMethod.PUT, requestBody, Product.class);

        String resourceUrl = "http://PRODUCT-SERVICE/products/"  + updateInfo.getProductId();
        Product p = restTemplate.getForObject(resourceUrl, Product.class);

        vo.setProduct(p);
//
        return vo;
    }
    public void   deleteUserAndProduct(ResponseTemplateVO templateVO){
        User user = templateVO.getUser();
        userRepository.delete(user);
        String productD = "http://PRODUCT-SERVICE/products/" + user.getProductId();
        restTemplate.delete(productD);

    }



}
