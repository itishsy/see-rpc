package com.see.rpc.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User extends Identity {
    /**
     *
     */
    private static final long serialVersionUID = 3573651620838436798L;
    private String loginName;
    //="登录用户名")
    private String plainPassword;
    //="用户登录密码")
    private String password;
    //="用户加密盐值")
    private String salt;
    //="用户名称")
    private String name;
    //="用户email")
    private String email;
    //="手机号")
    private String telephone;
    //="用户状态")
    private String status;
    //="用户编码")
    private String userNumber;
    //="登录用户名")
    private String reportMan1;
    //="是否离职（在职：0。离职：1） 默认是 0")
    //是否离职（在职：0。离职：1） 默认是 0
    private Integer isQuit = 0;
    //="是否是领导")
    private Integer ifManager;//是否是领导
    //="是否是本人（登录人）")
    private Integer ifOwner;//是否是本人（登录人）
    //="是否是管理员登录")
    private Integer ifAdmin;//是否是管理员登录
    //="QQ号")
    private String qqNumber;
    //="岗位")
    private String jobPosition;



    public Integer getIfAdmin() {
        return ifAdmin;
    }

    public void setIfAdmin(Integer ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public Integer getIfOwner() {
        return ifOwner;
    }

    public void setIfOwner(Integer ifOwner) {
        this.ifOwner = ifOwner;
    }

    public Integer getIfManager() {
        return ifManager;
    }

    public void setIfManager(Integer ifManager) {
        this.ifManager = ifManager;
    }

    public User() {
    }

    public User(Long id) {
        super.setId(id);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }

    public String getReportMan1() {
        return reportMan1;
    }

    public void setReportMan1(String reportMan1) {
        this.reportMan1 = reportMan1;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
}
