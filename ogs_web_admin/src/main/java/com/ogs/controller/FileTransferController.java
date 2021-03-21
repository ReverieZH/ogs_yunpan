package com.ogs.controller;

import com.obs.services.model.ObsObject;
import com.ogs.domain.Files;
import com.ogs.domain.OBS;
import com.ogs.service.FileService;
import com.ogs.utils.BucketObjectUtil;
import com.ogs.utils.DateUtil;
import com.ogs.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/filetransfer")
@CrossOrigin(origins="http:// 10.110.54.164:5000")
public class FileTransferController {

    @Autowired
    private FileService fileService;

    @Autowired
    private OBS obs;

    @Autowired
    private BucketObjectUtil bucketObjectUtil;

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(HttpServletRequest request, @RequestParam("uploadfile") MultipartFile mfile, @RequestParam(defaultValue = "0") Integer parentId, @RequestParam String parentDir) {
        Map<String,Object> map=new HashMap<>();
        boolean isSuccess=false;
        String filename=parentDir+"/"+mfile.getOriginalFilename();
        System.out.println("-----------------------"+filename);
        System.out.println("========================上传文件====================================");
        //华为云上传文件
        try{
            Integer statusCode = bucketObjectUtil.uploadFile(mfile.getInputStream(),filename);
            if(statusCode==0){
                isSuccess=false;
                map.put("isSuccess",isSuccess);
                map.put("code",2);
                map.put("info","存在同名文件");
                return map;
            }else{
                Integer uid= (Integer) request.getSession().getAttribute("uid");
                String type= FileUtil.getType(filename);
                String url=obs.getEndPoint()+"/"+filename;
                Files files=new Files(mfile.getOriginalFilename(),type,url, DateUtil.getSqlDate(),uid,0,new Double(mfile.getSize()),0,parentId);
                int upload = fileService.upload(files);
                if(upload==1){
                    isSuccess=true;
                    map.put("isSuccess",isSuccess);
                    map.put("info","上传成功");
                    map.put("code",0);
                    return map;
                }else{
                    isSuccess=false;
                    map.put("isSuccess",isSuccess);
                    map.put("info","上传失败");
                    map.put("code",1);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("info",e.getMessage());
            map.put("isSuccess",false);
        }
        return map;
    }


    @RequestMapping("/download")
    public void download(HttpServletResponse response,@RequestParam String fileName, @RequestParam String parentDir)  {
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        String filePath= parentDir+"/"+fileName;
        ObsObject obsfile = bucketObjectUtil.getFile(filePath);
        if(obsfile!=null){
            InputStream input = obsfile.getObjectContent();
            byte[] b = new byte[1024];
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ServletOutputStream os = null;
            try {
                os = response.getOutputStream();
                int len;
                while ((len=input.read(b)) != -1){
                    os.write(b, 0, len);
                }
                os.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
