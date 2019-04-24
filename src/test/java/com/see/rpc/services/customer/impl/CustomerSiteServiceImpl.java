package com.see.rpc.services.customer.impl;

import com.see.rpc.spring.RpcResource;
import com.see.rpc.spring.RpcService;
import com.see.rpc.common.CommonUtils;
import com.see.rpc.entity.Customer;
import com.see.rpc.entity.CustomerSiteOne;
import com.see.rpc.entity.User;
import com.see.rpc.services.customer.ICustomerService;
import com.see.rpc.services.customer.ICustomerSiteService;
import com.see.rpc.services.system.IUserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RpcService
public class CustomerSiteServiceImpl implements ICustomerSiteService {

    private List<CustomerSiteOne> siteOnes = new ArrayList<>();


    @PostConstruct
    private void mock(){
        for(int i=1;i<=500;i++) {
            CustomerSiteOne siteOne = new CustomerSiteOne(i);
            siteOne.setCode("S1000" + i);
            siteOne.setName("站点-" + i);
            siteOne.setCustId(i % 50 + 1);
            siteOnes.add(siteOne);
        }


//        userService.login("user-1","user-1");
    }

    @RpcResource
    private IUserService userService;

    @RpcResource
    private ICustomerService customerService;


    @Override
    public CustomerSiteOne getSiteOneById(Long id) {
        for(CustomerSiteOne siteOne : siteOnes){
            if(siteOne.getId().intValue()  ==  id.intValue())
                return siteOne;
        }
        return null;
    }

    @Override
    public List<CustomerSiteOne> getSiteOneList(int customerId) {
        List<CustomerSiteOne> list = new ArrayList<>();

        for(CustomerSiteOne siteOne : siteOnes){
            if(siteOne.getCustId() == customerId){
                list.add(siteOne);
            }
        }

        return list;
    }

    @Override
    public List<CustomerSiteOne> findCustSiteoneByOptrCompId(String optrCompId) {
        return null;
    }

    @Override
    public CustomerSiteOne selectSiteOneById(Long siteOneId) {
        for(CustomerSiteOne siteOne : siteOnes){
            if(CommonUtils.equals(siteOneId,siteOne.getId())){
                return siteOne;
            }
        }
        return null;
    }

    @Override
    public List<CustomerSiteOne> getAllSiteOne() {
        return siteOnes;
    }

    @Override
    public List<CustomerSiteOne> getAllSiteOneByCurrUser() {
        User user = userService.getCurrentUser();
        List<Customer> customers = customerService.getCustomerListByUserId(user.getId());
        List<CustomerSiteOne> list = new ArrayList<>();
        for (Customer customer : customers){
            for(CustomerSiteOne siteOne : siteOnes){
                if(siteOne.getCustId().equals(customer.getId()))
                    list.add(siteOne);
            }
        }
        return list;
    }

    @Override
    public User getServiceSiteOneById(int siteOneId) {
        return null;
    }

    @Override
    public User getSettlerSiteOneById(int siteOneId) {
        return null;
    }
}
