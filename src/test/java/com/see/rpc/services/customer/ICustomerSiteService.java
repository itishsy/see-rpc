package com.see.rpc.services.customer;


import com.see.rpc.common.BusinessException;
import com.see.rpc.entity.Customer;
import com.see.rpc.entity.CustomerSiteOne;
import com.see.rpc.entity.CustomerSiteTwo;
import com.see.rpc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户站点service接口
 *
 * @author Ldd 2014-11-10 11:00
 */
public interface ICustomerSiteService {

    CustomerSiteOne getSiteOneById(Long id) ;

    /**
     * 获取客户下的一级站点
     *
     * @param customerId 客户id
     * @return List<CustomerSiteOne>
     * 一级站点集合
     */
    List<CustomerSiteOne> getSiteOneList(int customerId);

    /**
     * 根据 操作公司id获取 客户信息
     *
     * @param optrCompId
     * @return
     */
    List<CustomerSiteOne> findCustSiteoneByOptrCompId(String optrCompId);

    CustomerSiteOne selectSiteOneById(Long siteOneId);

    List<CustomerSiteOne> getAllSiteOne();

    List<CustomerSiteOne> getAllSiteOneByCurrUser();

    /**
     * 通过一级站点id查询一级站点下的客服主管信息
     * @param siteOneId
     * @return
     */
    User getServiceSiteOneById(int siteOneId);

    /**
     * 通过一级站点id查询一级站点下的结算主管信息
     * @param siteOneId
     * @return
     */
    User getSettlerSiteOneById(int siteOneId);
}
