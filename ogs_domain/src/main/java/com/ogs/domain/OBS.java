package com.ogs.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="obs")
public class OBS {
    String endPoint;
    String sk;
    String ak;
    String bucketLocation;
    String bucketName;
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getBucketLocation() {
        return bucketLocation;
    }

    public void setBucketLocation(String bucketLocation) {
        this.bucketLocation = bucketLocation;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "OBS{" +
                "endPoint='" + endPoint + '\'' +
                ", sk='" + sk + '\'' +
                ", ak='" + ak + '\'' +
                ", bucketLocation='" + bucketLocation + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }
}
