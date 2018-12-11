package com.chenbing.Mybatis;


import com.chenbing.Mybatis.mapper.UserMapper;
import com.chenbing.Mybatis.model.User;

public class TestMybatis {

    public static void main(String[] args) {
        MySqlsession sqlsession=new MySqlsession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}
