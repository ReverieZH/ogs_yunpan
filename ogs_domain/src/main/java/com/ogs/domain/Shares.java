package com.ogs.domain;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
/**
 * 资源权限类
 */
@Table(name = "tb_shares")
public class Shares implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sid;        //分享id；主键自增
    private Integer fid;        //文件id；外键
    @Transient
    private File file;
    private String url;         //分享链接
    private String password;    //分享密码
    private Date shareTime;     //分享时间
    private String duration;    //有效期
    private Integer uid;        //分享者id；外键
    @Transient
    private Users users;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Shares{" +
                "sid=" + sid +
                ", fid=" + fid +
                ", file=" + file +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                ", shareTime=" + shareTime +
                ", duration='" + duration + '\'' +
                ", uid=" + uid +
                ", users=" + users +
                '}';
    }
}
