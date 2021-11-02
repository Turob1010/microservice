package com.Oydin.Userservice.VO;

import com.Oydin.Userservice.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListTemplateVO {

    private List<User> users;
    private List<Product> products;
}
