package com.ogs.service;

import com.ogs.domain.Users;

import java.util.List;

public interface UserService {

    public List<Users> findUserByName(String name);


    public Users login(Users user) ;

}
