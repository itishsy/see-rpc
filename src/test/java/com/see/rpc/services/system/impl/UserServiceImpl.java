package com.see.rpc.services.system.impl;

import com.see.rpc.spring.RpcResource;
import com.see.rpc.spring.RpcService;
import com.see.rpc.common.CommonUtils;
import com.see.rpc.entity.User;
import com.see.rpc.services.customer.ICustomerService;
import com.see.rpc.services.system.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RpcService
public class UserServiceImpl implements IUserService {

    private List<User> users = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(IUserService.class);

    @RpcResource
    private ICustomerService customerService;

    private User currentUser = null;

    @PostConstruct
    private void mock() {
        for (int i = 1; i <= 50; i++) {
            User user = new User((long) i);
            user.setLoginName("user-" + i);
            user.setPassword("user-" + i);
            user.setName("用户-" + i);
            users.add(user);
        }
    }

    @Override
    public List<User> findAllUser() {
        try {

            Thread.sleep(10*60*1000);

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String login(String loginName, String password) {
//        Random random =new Random();
        try {
            Thread.sleep(20 *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User user : users) {
            if (user.getLoginName().equals(loginName) && user.getPassword().equals(password)) {
                setCurrentUser(user);
                logger.info("登录成功:" + loginName + "," + password);
                return loginName + "登录成功";
            }
        }
        logger.info("登录失败:" + loginName + "," + password);
        return loginName + "登录失败";
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public String getUsersByCustomer(String id) {
//        List<User> users = Lists.newArrayList();
//        for (int i = 0; i < 100; i++) {
//            users.add(new User((long) i));
//        }
        System.out.println("接受到请求"+id);
        customerService.getAllCustomerList(id);
        return "正常";
    }

    @Override
    public void exception2Client() {

        throw new NullPointerException("system 空指针了！！！");
    }

    @Override
    public void loopInvoker() {
        System.out.println("userservice loopinvoker!!");
        customerService.loopInvoker();
    }


    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public User getUserById(String id) {
        Random random =new Random();
        try {
            Thread.sleep(random.nextInt(10) *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User user : users) {
            if (CommonUtils.equals(user.getId(), id))
                return user;
        }
        return null;
    }
}
