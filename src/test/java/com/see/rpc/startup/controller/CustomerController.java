package com.see.rpc.startup.controller;

import com.see.rpc.spring.RpcResource;
import com.see.rpc.common.BatchTest;
import com.see.rpc.entity.CustomerSiteOne;
import com.see.rpc.services.customer.ICustomerService;
import com.see.rpc.services.customer.ICustomerSiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-apps/spring-customer.xml")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @RpcResource
    private ICustomerSiteService customerSiteService;

    @RpcResource
    private ICustomerService customerService;

    @Test
    public void getSiteOne() {
        List<CustomerSiteOne> siteOnes = customerSiteService.getAllSiteOneByCurrUser();

        logger.info("" + siteOnes.size());
        for (CustomerSiteOne siteOne : siteOnes) {
            logger.info("" + siteOne);
        }
    }

    public void getSiteOneBatch(){
        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final String index = (i + 1) + "";
            Callable<Integer> task = () -> {
                try {
                    customerSiteService.getAllSiteOneByCurrUser();
                    return 1; //("user-" + index + "登录成功").equals(ret) ? 1 : 0;
                }catch (Exception e){
                    e.printStackTrace();
                    return 0;
                }
            };
            list.add(task);
        }
        new BatchTest(list).start();
    }
}
