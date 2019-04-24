package com.see.rpc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerSiteTwo {
    //"自动增长ID")
    private Integer id;

    //"以及站点关联ID")
    private Integer tCustomerSiteoneId;

    //"二级站点名称")
    private String name;

    //"申报单需要审核")
    private Byte needDeclareCheck;

    //"客户业务联系人")
    private String customerContact;

    //"0:无效、1:有效")
    private Byte isactive;

    //"创建人")
    private Integer createdUser;

    //"创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    //"修改人")
    private Integer modifyUser;

    //"更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    //"冻结日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date freezDate;

    //"冻结时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date freezTime;

    //"鏄?惁鍒犻櫎")
    private Byte isDelete;

    //"客户合同业务类型")
    private Integer customerType;

    //"客服专员ID")
    private Integer customerServiceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer gettCustomerSiteoneId() {
        return tCustomerSiteoneId;
    }

    public void settCustomerSiteoneId(Integer tCustomerSiteoneId) {
        this.tCustomerSiteoneId = tCustomerSiteoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getNeedDeclareCheck() {
        return needDeclareCheck;
    }

    public void setNeedDeclareCheck(Byte needDeclareCheck) {
        this.needDeclareCheck = needDeclareCheck;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact == null ? null : customerContact.trim();
    }

    public Byte getIsactive() {
        return isactive;
    }

    public void setIsactive(Byte isactive) {
        this.isactive = isactive;
    }

    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    //
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    //
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    //
    public Date getFreezDate() {
        return freezDate;
    }

    public void setFreezDate(Date freezDate) {
        this.freezDate = freezDate;
    }

    //
    public Date getFreezTime() {
        return freezTime;
    }

    public void setFreezTime(Date freezTime) {
        this.freezTime = freezTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(Integer customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tCustomerSiteoneId=").append(tCustomerSiteoneId);
        sb.append(", name=").append(name);
        sb.append(", needDeclareCheck=").append(needDeclareCheck);
        sb.append(", customerContact=").append(customerContact);
        sb.append(", isactive=").append(isactive);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifyUser=").append(modifyUser);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", freezDate=").append(freezDate);
        sb.append(", freezTime=").append(freezTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", customerType=").append(customerType);
        sb.append(", customerServiceId=").append(customerServiceId);
        sb.append("]");
        return sb.toString();
    }
}