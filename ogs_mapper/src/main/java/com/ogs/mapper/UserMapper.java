package com.ogs.mapper;

import com.ogs.domain.Users;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<Users> {

    @Select("select * from tb_users where name like #{name}")
    @Results({
            @Result(id = true,column = "uid",property = "uid"),
            @Result(column = "job_number",property = "jobNumber"),
            @Result(column = "name",property = "name"),
            @Result(column = "phone",property = "phone"),
            @Result(column = "email",property = "email"),
            @Result(column = "password",property = "password"),
            @Result(column = "status",property = "status"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "rid",property = "role",javaType = com.ogs.domain.Role.class,one = @One(select = "com.ogs.mapper.RoleMapper.selectByPrimaryKey")),
            @Result(column = "sex",property = "sex"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "login_time",property = "loginTime"),
            @Result(column = "net_disk_size",property = "netDiskSize"),
            @Result(column = "is_first_login",property = "isFirstLogin")
    })
    public List<Users> findUserByName(String name);
}
