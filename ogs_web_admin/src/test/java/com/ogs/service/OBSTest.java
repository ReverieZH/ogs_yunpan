package com.ogs.service;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import com.obs.services.model.fs.DropFolderRequest;
import com.obs.services.model.fs.RenameRequest;
import com.obs.services.model.fs.RenameResult;
import com.ogs.utils.BucketObjectUtil;
import com.ogs.utils.FileUtil;
import com.ogs.utils.Rename_String;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@MapperScan("com.ogs.mapper")
public class OBSTest {
    @Autowired
    private BucketObjectUtil bucketObjectUtil;

    @Test
    public void testIsExist(){
        ObsClient obsClient = bucketObjectUtil.getInstance();
        boolean exist = obsClient.doesObjectExist("obs-ogs-test", "ogs21011101/");
        System.out.println(exist);
    }

    @Test
    public void mkdir() throws IOException {
        Integer integer = bucketObjectUtil.uploadFile(new ByteArrayInputStream(new byte[0]),"test/tt/t2/");
        System.out.println(integer);
    }

    @Test
    public void open(){
        ObsObject file = bucketObjectUtil.getFile("reverie/obs-sdk-java-devg.pdf");
    }


    @Test
    public void testDelete() throws IOException {
        boolean file = bucketObjectUtil.removeFile("ogs21010901/home.html");
        System.out.println(file);
    }

    @Test
    public void testGet(){
        ObsClient obsClient = bucketObjectUtil.getInstance();
        DropFolderRequest dropFolderRequest=new DropFolderRequest("obs-ogs-test", "ogs21010901/reverie5");
        TaskProgressStatus taskProgressStatus = obsClient.dropFolder(dropFolderRequest);
        System.out.println(taskProgressStatus.getSucceedTaskNum());
    }

    @Test
    public void testListFile(){
        ObsClient obsClient = bucketObjectUtil.getInstance();
        ListObjectsRequest request = new ListObjectsRequest("obs-ogs-test");
// 设置文件夹对象名"dir/"为前缀
        request.setPrefix("ogs21010901/");
        request.setMaxKeys(1000);
        ObjectListing result;
        do{
            result = obsClient.listObjects(request);
            for (ObsObject obsObject : result.getObjects())
            {
                System.out.println("\t" + obsObject.getObjectKey());
                System.out.println("\t" + obsObject.getOwner());
            }
            request.setMarker(result.getNextMarker());
        }while(result.isTruncated());
    }

    @Test
    public void getDirObject() throws IOException {
        for (ObsObject obsObject : bucketObjectUtil.getDirObjectList("ogs21010901/reverie/11"))
        {
            System.out.println("\t" + obsObject.getObjectKey());
            System.out.println("\t" + obsObject.getOwner());
        }
    }

    @Test
    public void copyFileTest() throws IOException {
        ObsClient obsClient = bucketObjectUtil.getInstance();
        obsClient.copyObject("obs-ogs-test","ogs21010901/数据结构.docx","obs-ogs-test","ogs21010001/1.docx");
        obsClient.close();
    }
    @Test
    public void copyDirTest() throws IOException {
        bucketObjectUtil.copyFileList("ogs21010901/reverie","ogs21010001");
    }

    @Test
    public void DirStringTest() throws IOException {
        String desdDrName="ogs21010001";

        List<ObsObject> list = bucketObjectUtil.getDirObjectList("ogs21010901/reverie");
        String orgin=list.get(0).getObjectKey();
        int i = orgin.lastIndexOf('/', (orgin.lastIndexOf("/") - 1));

        for (ObsObject obs : list)
        {
//            System.out.println("\t" + obsObject.getObjectKey());
//            System.out.println("\t" + obsObject.getOwner());
            if(FileUtil.isDir(obs.getObjectKey())){
                System.out.println("---------sorce:"+obs.getObjectKey()+"----------des:"+desdDrName+"/"+obs.getObjectKey().substring(i+1));
//                obsClient.putObject(bucketName, desdDrName+"/"+obs.getObjectKey(), new ByteArrayInputStream(new byte[0]));
            }else{
                System.out.println("======sorce:"+obs.getObjectKey()+"===============des:"+desdDrName+"/"+obs.getObjectKey().substring(i+1));
//                obsClient.copyObject(bucketName, Rename_String.subPrefix(obs.getObjectKey()),bucketName,desdDrName+"/"+Rename_String.subPrefix(obs.getObjectKey()));
            }
        }
    }


    @Test
    public void renameFIleTest() throws IOException {
        /*Integer integer = bucketObjectUtil.renameFile("11/", "22/");
        System.out.println(integer.intValue());*/
        ObsClient obsClient = bucketObjectUtil.getInstance();
//        System.out.println(obsClient.doesObjectExist("obs-ogs-test", "11/home.html"));
        RenameRequest renameRequest=new RenameRequest("obs-ogs-test","home.html","22.html");
        RenameResult renameResult = obsClient.renameFile(renameRequest);
        System.out.println(renameResult.getStatusCode());
    }

    @Test
    public void previewTest() throws IOException {
        String preview = bucketObjectUtil.preview("ogs21010901/166_IO_开篇.mp4");
        System.out.println(preview);
    }

    @Test
    public void rename(){
        ObsClient obsClient = bucketObjectUtil.getInstance();
        CopyObjectResult copyObjectResult = obsClient.copyObject("obs-ogs-test", "2", "obs-ogs-test", "null/");
        System.out.println(copyObjectResult);
    }

}
