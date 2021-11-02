package com.Oydin.Userservice.Entity;



import javax.persistence.*;

@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String userName;
    private Integer age;
    private Integer productId;

    public User(Integer userId, String userName, Integer age, Integer paymentId) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.productId = paymentId;
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
