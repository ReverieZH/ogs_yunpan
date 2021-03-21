package com.ogs.domain;

public class Result {
    private boolean issuccess;
    private String info;
    private Integer statusCode;

    public Result() {
    }

    public Result(boolean issuccess, String info, Integer statusCode) {
        this.issuccess = issuccess;
        this.info = info;
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
