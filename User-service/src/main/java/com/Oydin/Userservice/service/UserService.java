package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Exception.UserAlreadyExistsException;
import com.Oydin.Userservice.Exception.UserNotFoundException;
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
public class UserService implements UserServiceInt {


    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    User user = new User();
    ResponseTemplateVO vo = new ResponseTemplateVO();
    ResponseListTemplate voList = new ResponseListTemplate();

    @Override
    public User createUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            throw new UserAlreadyExistsException();
        }
        User createUser = userRepository.save(user);
        return createUser;
    }

    public User getById(Integer userId){ return userRepository.findById(userId).get();
    }

    public List<User> getAll(){
       List<User> users = userRepository.findAll();
       return users;
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

    @Override
    public User updateUser(User userDetails,Integer userId) throws UserNotFoundException {
        User user;
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            user = userRepository.findById(userId).get();
            User updateUser = userRepository.save(user);
            return updateUser;
        }
    }
    public ResponseTemplateVO updateUserAndProduct(ResponseTemplateVO templateVO){

        User user1= userRepository.save(user);
        Product updateInfo = new Product();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Product> requestBody = new HttpEntity<>(updateInfo, headers);

        restTemplate.exchange("http://PRODUCT-SERVICE/products/update", HttpMethod.PUT, requestBody, Product.class);

        String resourceUrl = "http://PRODUCT-SERVICE/products/"  + updateInfo.getProductId();
        Product p = restTemplate.getForObject(resourceUrl, Product.class);
        templateVO.setUser(user1);
        templateVO.setProduct(p);
        return templateVO;
    }
    public ResponseTemplateVO   deleteUserAndProduct(ResponseTemplateVO templateVO){
        User user = templateVO.getUser();
        userRepository.delete(user);
        String productD = "http://PRODUCT-SERVICE/products/" + user.getProductId();
       restTemplate.delete(productD);
        Product product = restTemplate.getForObject(productD, Product.class);
        templateVO.setProduct(product);
        templateVO.setUser(user);
        return templateVO;

    }



}
