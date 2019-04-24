package com.see.rpc.services.system;

import com.see.rpc.entity.User;

import java.util.List;

public interface IUserService  {


	List<User> findAllUser();

	String login(String loginName,String password);

	User getUserById(String id);

	User getCurrentUser();

	/**
	 * 通过客户查找所属的客服人员
	 * @param id
	 * @return
	 */
	String getUsersByCustomer(String id);

	void exception2Client();

    void loopInvoker();
}
