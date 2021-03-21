package com.ogs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件类
 */
@Table(name = "tb_files")
public class Files implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;         //文件id；主键自增
    private String fileName;    //文件名
    private String type;        //文件类型
    private String url;         //文件路径
    private Date uploadTime;   //文件上传时间
    private Integer uid;        //文件所属用户id；外键
    @Transient
    private Users users;
    private Integer isDel;      //文件是否进入回收站：0—未删除、1—已删除
    private Double size;        //文件大小
    private Integer isDir;      //是否为目录
    private Integer parentId;   //父级目录id

    @Transient
    private Integer oldfid;

    public Files() {
    }

    public Files(String fileName, String type, String url, Date uploadTime, Integer uid, Integer isDel, Double size, Integer isDir, Integer parentId) {
        this.fileName = fileName;
        this.type = type;
        this.url = url;
        this.uploadTime = uploadTime;
        this.uid = uid;
        this.isDel = isDel;
        this.size = size;
        this.isDir = isDir;
        this.parentId = parentId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getIsDir() {
        return isDir;
    }

    public void setIsDir(Integer isDir) {
        this.isDir = isDir;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOldfid() {
        return oldfid;
    }

    public void setOldfid(Integer oldfid) {
        this.oldfid = oldfid;
    }

    @Override
    public String toString() {
        return "files{" +
                "fid=" + fid +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", uploadTime=" + uploadTime +
                ", uid=" + uid +
                ", users=" + users +
                ", isDel=" + isDel +
                ", size=" + size +
                ", isDir=" + isDir +
                ", parentId=" + parentId +
                '}'+"\n";
    }
}
