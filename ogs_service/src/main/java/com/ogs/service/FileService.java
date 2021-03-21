package com.ogs.service;

import com.ogs.domain.Files;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface FileService {
    public Files selectOne(Integer fid, Integer uid);


    public List<Files> findAllFilesByUid(Integer uid);

    public List<Files> findAllFilesByUidAndParentId(Integer uid,Integer parentId);

    public int upload(Files files);

    Integer batchInsertFile(List<Files> files) throws Exception;

    public int mkdir(Files files);

    public int delete(Files files);

    public int update(Files files);

    public int copy(Files sourceFiles,Integer desParentId,Integer desUid,String desparentDir) throws Exception;

    public int move(Files sourceFiles,Integer desParentId,Integer desUid,String desparentDir) throws Exception;

    public List<Files> search(String fileName,Integer uid);

    public int mkFirstDir(Integer uid,String jobNumber) throws ParseException, IOException;

    public List<Files> typeSearch(Integer uid,String type);
}
