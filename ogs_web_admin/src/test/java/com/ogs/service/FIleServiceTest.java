package com.ogs.service;

import com.ogs.domain.Files;
import com.ogs.mapper.FileMapper;
import com.ogs.utils.BucketObjectUtil;
import com.ogs.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@MapperScan("com.ogs.mapper")
public class FIleServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private BucketObjectUtil bucketObjectUtil;

    @Test
    public void test() {
        List<Files> files = fileService.findAllFilesByUid(2);
        System.out.println(files);
    }

    @Test
    public void testUidAndParentId(){
        List<Files> files = fileService.findAllFilesByUidAndParentId(2, 2);
        System.out.println(files);
    }

    @Test
    public void testUpload() throws ParseException {
        Files files=new Files("文本5","txt","wwww5", new Date(),new Integer(2),new Integer("0"),new Double("1024"),new Integer("0"),new Integer("2"));
        int upload = fileService.upload(files);
        System.out.println(upload);
    }

    @Test
    public void testUpdate(){
        Files files=new Files();
        files.setFid(14);
        files.setFileName("22");
        int update = fileService.update(files);
        System.out.println(update);
    }


    @Test
    public void testCopy() throws Exception {
        Files sourcefiles=new Files();
        sourcefiles.setFid(2);
        sourcefiles.setUid(2);
        int copy = fileService.copy(sourcefiles, 1, 1, "test");
        System.out.println(copy);
    }

    @Test
    public void insertAndGetId() throws ParseException {
        Files files=new Files("文本5","txt","wwww5", DateUtil.getSqlDate(),new Integer(2),new Integer("0"),new Double("1024"),new Integer("0"),new Integer("1"));
        fileMapper.insert(files);
        System.out.println(files);
    }

    @Test
    public void serarchTest(){
        List<Files> files = fileService.search("数据", 2);
        System.out.println(files);
    }

    @Test
    public void mkFirstDirTest() throws IOException, ParseException {
        int firstDir = fileService.mkFirstDir(6, "ogs21011101");
        System.out.println(firstDir);
    }

    @Test
    public void typeSearch(){
        List<Files> files = fileService.typeSearch(2, "html");
        System.out.println(files);
    }
}