package com.ogs.service.impl;

import com.ogs.domain.Files;
import com.ogs.mapper.FileMapper;
import com.ogs.service.FileService;
import com.ogs.utils.BucketObjectUtil;
import com.ogs.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service("fileService")
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private BucketObjectUtil bucketObjectUtil;

    public Files selectOne(Integer fid, Integer uid){
        Files files=new Files();
        files.setUid(uid);
        files.setFid(fid);
        return fileMapper.selectOne(files);
    }

    public List<Files> findAllFilesByUid(Integer uid){
        Files files=new Files();
        files.setUid(uid);
        return fileMapper.select(files);
    }



    public List<Files> findAllFilesByUidAndParentId(Integer uid,Integer parentId){
        Files files=new Files();
        files.setUid(uid);
        files.setParentId(parentId);
        return fileMapper.select(files);
    }

    public int upload(Files files){
        int insert = fileMapper.insert(files);
        return insert;
    }

    public Integer batchInsertFile(List<Files> files) throws Exception {
        return fileMapper.batchInsert(files);
    }


    public int mkdir(Files files){
        int insert=fileMapper.insert(files);
        return insert;
    }

    public int delete(Files files){
        int delete = fileMapper.deleteByPrimaryKey(files);
        return delete;
    }

    public int update(Files files){
        int update = fileMapper.updateByPrimaryKeySelective(files);
        return update;
    }

    public int copy(Files sourceFiles,Integer desParentId,Integer desUid,String desparentDir) throws Exception {
        List<Files> files=new ArrayList<>();   //存储修改后的列表
        int id=1;
        Queue<Files> filesQueue=new ArrayDeque<>();     //遍历队列

        Files rootfile = fileMapper.selectOne(sourceFiles);   //查询根文件
        System.out.println("====root==="+rootfile);
        rootfile.setOldfid(rootfile.getOldfid());
        rootfile.setFid(null);
        rootfile.setParentId(desParentId);                      //修改根文件
        rootfile.setUid(desUid);
        rootfile.setUrl(desparentDir+"/"+rootfile.getFileName());

        rootfile.setFid(id++);//模拟插入
        filesQueue.add(rootfile);

        Files father;
        Files queryFiles=new Files();
        String parentPath;
        while(!filesQueue.isEmpty()){
//            System.out.println("111111111111111111111111");
            father=filesQueue.remove();
//            fileMapper.insert(rootfile);
//            filesQueue.add(father);
//            System.out.println("========fathe======="+father);
            Integer uid=father.getUid();
            Integer parentId=father.getOldfid();
            Integer newparentId=father.getFid();
            parentPath=father.getUrl();

            queryFiles.setUid(sourceFiles.getUid());
            queryFiles.setParentId(parentId);
//            List<Files> childs = fileMapper.findFilesByUidAndParendId(uid, parentId);
            List<Files> childs = fileMapper.select(queryFiles);
//            System.out.println("=====childs====="+childs);
//            int length=f.size();
            for (Files c:childs) {
                c.setOldfid(c.getFid());
                c.setFid(null);
                c.setUid(desUid);
                c.setUrl(desparentDir+"/"+parentPath+"/"+c.getFileName());
                c.setParentId(newparentId);
                c.setUploadTime(DateUtil.getSqlDate());
                c.setFid(id++);//模拟插入
                files.add(c);
            }
        }
        System.out.println(files);
//       return fileMapper.batchInsert(files);
        return 0;
    }

    public int move(Files sourceFiles,Integer desParentId,Integer Uid,String desparentDir) throws Exception {
        copy(sourceFiles,desParentId,Uid,desparentDir);
        return delete(sourceFiles);
    }

    public List<Files> search(String fileName,Integer uid){
        return fileMapper.search("%"+fileName+"%",uid);
    }

    public int mkFirstDir(Integer uid,String jobNumber) throws ParseException, IOException {
        Integer integer = bucketObjectUtil.uploadFile(new ByteArrayInputStream(new byte[0]),jobNumber+"/");
        Files files=new Files(jobNumber,"dir",jobNumber,DateUtil.getSqlDate(),uid,0,0.0,1,1);
        fileMapper.insert(files);
        return files.getFid();
    }

    @Override
    public List<Files> typeSearch(Integer uid, String type) {
        Files files=new Files();
        files.setUid(uid);
        files.setType("."+type);
        return fileMapper.select(files);
    }
}
