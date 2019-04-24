package com.see.rpc.entity;

/**
 * 从CRM导入的基础客户
 * 实体类对应的数据表为：  t_customer
 * @author Mr. Li
 * @date 2018-03-13 10:31:37
 */
public class Customer implements Cloneable {
    // "自动增长ID")
    private Integer id;

    // "客户全称")
    private String name;

    // "客户简称")
    private String shortName;

    // "客户代码")
    private String code;

    // "二级码")
    private String secondCode;


    private Long userId;

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private static Customer createObj = new Customer();
    public static Customer getObj() {
        try {
            return (Customer) createObj.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Customer(Integer id) {
        this.id = id;
    }
    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode == null ? null : secondCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", shortName=").append(shortName);
        sb.append(", code=").append(code);
        sb.append(", secondCode=").append(secondCode);
        sb.append("]");
        return sb.toString();
    }


}