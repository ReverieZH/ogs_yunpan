package com.ogs.service;

import com.ogs.domain.Role;
import com.ogs.domain.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@MapperScan("com.ogs.mapper")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test(){
        List<Users> users= userService.findUserByName("测");
        for (Users user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testlogin(){
        Users users=new Users();
        users.setUid(2);
        users.setPassword("1");
        Users login = userService.login(users);
        System.out.println(login);
    }
}
