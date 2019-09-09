package com.chenbing.Json;

import com.alibaba.fastjson.JSON;
import com.chenbing.Json.beans.User;

public class JsonTest {

    public static void main(String[] args){
        User user = new User();
        user.setAge("45");
        user.setName("tom");
        System.out.println("--------------"+ JSON.toJSONString(user));
    }
}
