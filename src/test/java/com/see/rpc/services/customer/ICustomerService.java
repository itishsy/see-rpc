package com.see.rpc.services.customer;


import com.see.rpc.entity.Customer;
import com.see.rpc.entity.User;
import com.see.rpc.model.Model;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 客户站点service接口
 *
 * @author Ldd 2014-11-10 11:00
 */
public interface ICustomerService {

    /**
     * 通过当前用户的角色获得客户列表
     *
     * @return
     * @author Danny.w
     * 2014年12月12日
     */
    List<Customer> getCustomerListByCurrentUser();

    /**
     * 通过当前用户的角色获得客户列表
     *
     * @return
     * @author Danny.w
     * 2014年12月12日
     */
    List<Customer> getCustomerListByUserId(Long userId);

    /**
     * 通过当前用户的角色获得客户列表
     *
     * @return
     * @author Danny.w
     * 2014年12月12日
     * @param id
     */
    List<Customer> getAllCustomerList(String id);

    List<Customer> getAllCustomerByUserId(Long userId);

    String getBigObj2Server(List<User> users);

    List<User> getBigObj2Client(String requestUuid);

    void customizeObject2Server(Model model) throws InvocationTargetException, IllegalAccessException;

    Model customizeObject2Client() throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException;

    void exception2Client();

    void loopInvoker();
}
