package com.ogs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 用户类
 */
@Table(name = "tb_users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;            //用户id；主键自增
    private String jobNumber;       //工号
    private String name;            //姓名
    private String phone;           //手机号
    private String email;           //邮箱
    private String password;        //密码
    private Integer status;         //用户状态：0—开启状态；1—禁用状态；2—已删除状态
    @Transient
    private String statusStr;       //用户状态字符串
    private String rid;             //角色id，外键
    @Transient
    private Role role;              //角色id，外键
    private String sex;             //性别
    private Date createTime;        //创建时间
    private Date loginTime;          //最近登录时间
    private BigInteger netDiskSize; //剩余容量，初始容量5TB
    private Integer isFirstLogin;   //是否为首次登录状态：0—否；1—是；

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (status != null) {
            if (status == 0)
                statusStr = "开启状态";
            if (status == 1)
                statusStr = "禁用状态";
            if (status == 2)
                statusStr = "已删除状态";
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public BigInteger getNetDiskSize() {
        return netDiskSize;
    }

    public void setNetDiskSize(BigInteger netDiskSize) {
        this.netDiskSize = netDiskSize;
    }

    public Integer getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(Integer isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", jobNumber='" + jobNumber + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", rid='" + rid + '\'' +
                ", role=" + role +
                ", sex='" + sex + '\'' +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                ", netDiskSize=" + netDiskSize +
                ", isFirstLogin=" + isFirstLogin +
                '}';
    }
}
