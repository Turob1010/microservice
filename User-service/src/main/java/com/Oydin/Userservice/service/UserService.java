package com.Oydin.Userservice.service;

import com.Oydin.Userservice.Entity.User;
import com.Oydin.Userservice.Repository.UserRepository;
import com.Oydin.Userservice.VO.Product;
import com.Oydin.Userservice.VO.ResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.http.HttpHeaders;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    ResponseTemplateVO vo = new ResponseTemplateVO();

    String resourceUrl = "http://localhost:4084/products/";

    public User createUser (User user){
        return userRepository.save(user);
    }

    public ResponseTemplateVO saveUserAndProduct(User user, Integer productId,String productName,Double price) {
        User user1 = userRepository.save(user);
        Product product1 = new Product(productId,productName,price);
        HttpEntity<Product> requestBody = new HttpEntity<>(product1);
        ResponseEntity<Product> product =
                 restTemplate.postForEntity(resourceUrl +"saveproduct", requestBody, Product.class);
        Product product2 = product.getBody();
        vo.setUser(user1);
        vo.setProduct(product2);
        return vo;
    }

    public User getById(Integer userId){ return userRepository.getById(userId);
    }

    public ResponseTemplateVO getUserWithProduct(Integer userId){
        ResponseTemplateVO vo  = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        Product product = restTemplate.getForObject(resourceUrl + user.getProductId(),Product.class);
        vo.setUser(user);
        vo.setProduct(product);
        return vo;
    }
    public List<User> getAll(){
       List<User> users = userRepository.findAll();
       return users;
    }
    public void  getAllUsersWithProducts(){
      //  List<User> users = userRepository.findAll();
       String GET_ALL_PRODUCT =  "http://localhost:4084/products/getall";
    Product[] list = restTemplate.getForObject(GET_ALL_PRODUCT, Product[].class);

//
//		if (list != null) {
//        for (Product e : list) {
//           vo.setProduct(e);
//        }
//    }
         getAll();
    }
    public ResponseTemplateVO updateUserAndProduct(User user,){
        User user1 = userRepository.save(user);
        user1.setUserId(user.getUserId());
        user1.setUserName(user.getUserName());
        user1.setAge(user.getAge());
        user1.setProductId(user.getProductId());

        Product updateInfo = new Product(12, "Tom", 4780.0);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Product> requestBody = new HttpEntity<>(updateInfo, headers);
            String URL_PRODUCT_UPDATE =  "http://localhost:4084/products/update";
       restTemplate.put(URL_PRODUCT_UPDATE, requestBody, new Object[] {});
        String uriProductId = resourceUrl + user.getProductId();

        Product p = restTemplate.getForObject(uriProductId, Product.class);
        vo.setUser(user1);
        vo.setProduct(p);
        return vo;
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

    public ResponseTemplateVO deleteUserAndProduct(User user){
        User user1 = userRepository.deleteUser(user);
        String uri = resourceUrl  + user.getProductId();
        restTemplate.delete(uri);

        vo.setUser(user1);
        vo.setProduct(uri);
        return vo;
    }


}
