package com.see.rpc.startup.controller;

import com.see.rpc.spring.RpcResource;
import com.see.rpc.common.BasicTest;
import com.see.rpc.common.BatchTest;
import com.see.rpc.entity.User;
import com.see.rpc.services.system.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-apps/spring-system.xml")
public class SystemController {


    @RpcResource
    private IUserService userService;

    @Test
    public void loginBatch() {
        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            final String index = (i + 1) + "";
            Callable<Integer> task = () -> {
                String ret = userService.login("user-" + index, "user-" + index);
                return ("user-" + index + "登录成功").equals(ret) ? 1 : 0;
            };
            list.add(task);
        }
        new BatchTest(list).start();
    }

    @Test
    public void login(){
        String ret = userService.login("user-1","user-1");
        System.out.println(ret);
    }

    public void findAllUser(){
        List<User> users = userService.findAllUser();
        System.out.println(users);
    }

    public void writeBigData(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<1085;i++) {
            stringBuilder.append("测试写入数据超过水位默认值大小为六十四。");
        }
        for(int i=0;i<55;i++){
            stringBuilder.append("哈");
        }
        String s1 = stringBuilder.toString() + "." ; //65536 - 32 = 65504 , 65436
        userService.getUserById(s1);
    }

}
