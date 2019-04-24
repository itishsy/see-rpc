package com.see.rpc.services.customer.impl;

import com.google.common.collect.Lists;
import com.see.rpc.spring.RpcResource;
import com.see.rpc.spring.RpcService;
import com.see.rpc.entity.Customer;
import com.see.rpc.entity.User;
import com.see.rpc.model.Model;
import com.see.rpc.services.customer.ICustomerService;
import com.see.rpc.services.system.IUserService;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@RpcService
public class CustomerServiceImpl implements ICustomerService {


    private List<Customer> customers = new ArrayList<>();

    @PostConstruct
    private void mock(){
        for(int i=1;i<=50;i++) {
            Customer customer = new Customer(i);
            customer.setCode("C1000" + i);
            customer.setName("客户-" + i);
            customer.setUserId(new Long(i % 10 + 1));
            customers.add(customer);
        }
    }

    @RpcResource
    private IUserService userService;

    @RpcResource
    private ICustomerService customerService;

    @Override
    public List<Customer> getCustomerListByCurrentUser() {
        Long userId = userService.getCurrentUser().getId();
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getUserId().equals(userId))
                list.add(customer);
        }
        return list;
    }

    @Override
    public List<Customer> getCustomerListByUserId(Long userId) {
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getUserId().equals(userId))
                list.add(customer);
        }
        return list;
    }

    @Override
    public List<Customer> getAllCustomerList(String id) {
        System.out.println("接受到请求"+id);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomerByUserId(Long userId) {
//        List<CustomerStartup> customers = Lists.newArrayList();
//        for (int i = 0; i < 100; i++) {
//            customers.add(new CustomerStartup(i, "customer" + i));
//        }
        userService.findAllUser();
        return customers;
    }

    @Override
    public String getBigObj2Server(List<User> users) {
        System.out.println("处理完毕");
        return "处理完毕";
    }

    @Override
    public List<User> getBigObj2Client(String requestUuid) {
        System.out.println("响应到请求 "+requestUuid);
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setEmail(UUID.randomUUID().toString());
            user.setName(UUID.randomUUID().toString());
            user.setId((long) i);
            user.setIfAdmin(Integer.MAX_VALUE);
            user.setIfOwner(Integer.MAX_VALUE);
            user.setIsQuit(Integer.MAX_VALUE);
            user.setJobPosition("abc" + i);
            user.setLoginName("dddd" + i);
            users.add(user);
        }
        return users;
    }

    @Override
    public void customizeObject2Server(Model model) throws InvocationTargetException, IllegalAccessException {
        for(Field field:model.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                System.out.println(field.getName()+"---------------------------->");
                for(Annotation anno:field.getAnnotations()) {
                    for(Method m:anno.annotationType().getDeclaredMethods()) {
                        System.out.println("=============>"+m.getReturnType());
                        System.out.println(m.invoke(anno) + "////////////////////////////");
                    }
                }
            }
        }

        for(Field field:model.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                System.out.println(field.getName() + ":========>"+field.get(model));
            }
        }
    }

    @Override
    public Model customizeObject2Client() throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
        return null;
    }

    @Override
    public void exception2Client() {
        userService.exception2Client();
    }

    @Override
    public void loopInvoker() {
        userService.loopInvoker();
        System.out.println("customerservice loopinvoker!!");
    }

}
