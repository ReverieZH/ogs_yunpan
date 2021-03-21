package com.ogs.service.impl;

import com.ogs.domain.Users;
import com.ogs.mapper.UserMapper;
import com.ogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<Users> findUserByName(String name){
        String nameStr = "%" + name + "%";
        return userMapper.findUserByName(nameStr);
    }

    public Users login(Users user) {
        String pass_md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(pass_md5);
        return userMapper.selectOne(user);
    }

}
