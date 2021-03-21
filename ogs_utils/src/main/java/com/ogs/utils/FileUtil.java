package com.ogs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static String getType(String fileName){
        int lastIndexOf = fileName.lastIndexOf(".");
        //获取文件的后缀名 .jpg
        String suffix = fileName.substring(lastIndexOf);
        return suffix;
    }

    // 扫描文件夹下所有对象
    public static List<File> listFiles (File files){
        File[] fs =files.listFiles();
        List<File> list=new ArrayList<>();
        assert fs != null;
        if (fs.length < 1) {
            // 如果是空文件夹也需要上传，将其添加到列表中
            list.add(files);
        } else {
            for (File f : fs) {
                if (f.isDirectory()) {
                    listFiles(f);
                }
                if (f.isFile()) {
                    // 添加待上传对象到列表中
                    list.add(f);
                }
            }
        }
        return list;
    }

    public static boolean isDir(String objectKey){
        if(objectKey.endsWith("/"))
            return true;
        else
            return false;
    }


    public static String getPrintSize(double size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

}
