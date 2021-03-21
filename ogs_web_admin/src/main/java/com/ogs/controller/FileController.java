package com.ogs.controller;

import com.ogs.domain.Files;
import com.ogs.domain.OBS;
import com.ogs.service.FileService;
import com.ogs.utils.BucketObjectUtil;
import com.ogs.utils.DateUtil;
import com.ogs.utils.Rename_String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/files")
@CrossOrigin(origins="http:// 10.110.54.164:5000")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private OBS obs;

    @Autowired
    private BucketObjectUtil bucketObjectUtil;


/*
    @RequestMapping("/main")
    public String fileList(HttpServletRequest request, @RequestParam Integer parentId,String parentDir){
        Map<String,Object> map=new HashMap<>();
        Integer uid= (Integer) request.getSession().getAttribute("uid");   //获取用户的uid
        if(parentDir==null){
            parentDir= String.valueOf(request.getSession().getAttribute("jobNumber"));
            System.out.println("==================parentDir1==============="+parentDir);
        }
//        request.getSession().setAttribute("currentPath",parentDir);
        List<Files> files = fileService.findAllFilesByUidAndParentId(uid,parentId);     //根据uid和parentid获取文件列表
        System.out.println(files);
        request.setAttribute("filelist",files);
        request.setAttribute("parentId",parentId);
        request.setAttribute("parentDir",parentDir);
        return "filelist";
    }*/


    @RequestMapping("/main")
    @ResponseBody
    public Map<String,Object> fileList(HttpServletRequest request, @RequestParam Integer parentId,String parentDir){
        Map<String,Object> map=new HashMap<>();
        Integer uid= (Integer) request.getSession().getAttribute("uid");   //获取用户的uid
        if(parentDir==null){
            parentDir= String.valueOf(request.getSession().getAttribute("jobNumber"));
            System.out.println("==================parentDir1==============="+parentDir);
        }
//        request.getSession().setAttribute("currentPath",parentDir);
        List<Files> files = fileService.findAllFilesByUidAndParentId(uid,parentId);     //根据uid和parentid获取文件列表
        if(files!=null){
            map.put("issuccess",true);
            map.put("filelist",files);
            map.put("parentId",parentId);
            map.put("parentDir",parentDir);
        }else{
            map.put("issuccess",false);
        }
        return map;
    }

    //创建文件夹
    @RequestMapping("/mkdir")
    @ResponseBody
    public Map<String,Object> mkdir(HttpServletRequest request,String dirName,Integer parentId,Integer uid,@RequestParam String parentDir) throws IOException, ParseException {
        boolean issuccess=false;
        Map<String,Object> map=new HashMap<>();
//        Integer uid= (Integer) request.getSession().getAttribute("uid");
        Files files=new Files(dirName,"dir",parentDir+"/"+dirName,DateUtil.getSqlDate(),uid,0,0.0,1,parentId);
//        files.setUid(uid);
        files.setUploadTime(DateUtil.getSqlDateByFormatString("yyyy-MM-dd", DateUtil.getCurrentTime()));
//        String currentpath= String.valueOf(request.getSession().getAttribute("currentPath"));

        Integer integer = bucketObjectUtil.uploadFile(new ByteArrayInputStream(new byte[0]), parentDir+"/"+files.getFileName()+"/");
        if(integer==0){
            issuccess=false;
            map.put("isSuccess",issuccess);
            map.put("info","存在同名文件");
            return map;
        }else{
            int mkdir = fileService.mkdir(files);
            if(mkdir==1){
                issuccess=true;
                map.put("isSuccess",issuccess);
                map.put("info","创建成功");
                return map;
            }else {
                issuccess=false;
                bucketObjectUtil.removeFile(parentDir+"/"+files.getFileName()+"/");
                map.put("isSuccess",issuccess);
                map.put("info","创建失败");
                return map;
            }
        }
    }


    //删除文件
    @PostMapping("delete")
    @ResponseBody
    public Map<String,Object> deleteFile(HttpServletRequest request,Files files,@RequestParam String parentDir){
        Map<String,Object> map=new HashMap<>();
        boolean isSuccess=false;
        String filename=parentDir+"/"+files.getFileName();
        try {
            if(files.getIsDir().intValue()==0) {
                isSuccess = bucketObjectUtil.removeFile(filename);
            }else{
                isSuccess=bucketObjectUtil.removeDir(filename);
            }
            System.out.println("==================isSuccess=============="+isSuccess);
            if(isSuccess==true){
                fileService.delete(files);
                map.put("isSuccess",isSuccess);
                map.put("info","删除成功");
                return map;
            }else{
                map.put("isSuccess",isSuccess);
                map.put("info","删除失败");
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("isSuccess",isSuccess);
        map.put("info","出错");
        return map;
    }


    @PostMapping
    @ResponseBody
    public Map<String,Object> renameFile(HttpServletRequest request,HttpServletResponse response,String oldName,String newName,String parentDir){
        Map<String,Object> map=new HashMap<>();
        try {
            Integer integer = bucketObjectUtil.renameFile(oldName, newName);
            if(integer.intValue()==0){
                map.put("isSuccess",false);
                map.put("info","不存在");
                return map;
            }else {
                map.put("isSuccess",true);
                map.put("info","重命名成功");
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("isSuccess",false);
        map.put("info","出错");
        return map;
    }

    @PostMapping("/createshare")
    @ResponseBody
    public Map<String,Object>  craeteShare(HttpServletRequest request,@RequestParam Integer fid){
        Map<String,Object> map=new HashMap<>();
        Integer uid= (Integer) request.getSession().getAttribute("uid");
        String reqeustUrl= Rename_String.getUrl(String.valueOf(request.getRequestURL()));

        String url=reqeustUrl+"/"+"share?uid="+uid+"&fid="+fid;
        System.out.println(url);
        map.put("url",url);
        return map;
    }

    @PostMapping("/showshare")
    @ResponseBody
    public Map<String,Object> showshare(HttpServletRequest request, @RequestParam Integer uid,@RequestParam Integer fid){
        Map<String,Object> map=new HashMap<>();

        Files files=fileService.selectOne(fid,uid);
        if(files!=null){
            map.put("issuccess",true);
            map.put("files",files);
        }else{
            map.put("issuccess",false);
        }
        return map;
    }

    @PostMapping("/saveshare")
    @ResponseBody
    public Map<String,Object> shareFiles(HttpServletRequest request,@RequestParam Integer fid,@RequestParam Integer uid,@RequestParam Integer parentId, String sourceDir,String desDir){
        Map<String,Object> map=new HashMap<>();
        boolean isSuccess=false;
        Integer desuid= (Integer) request.getSession().getAttribute("uid");
        try {
            bucketObjectUtil.copyFileList(sourceDir,desDir);
            Files sourcefiles=new Files();
            sourcefiles.setFid(fid);
            sourcefiles.setUid(uid);
            new Thread(()-> {
                try {
                    fileService.copy(sourcefiles,parentId,desuid,desDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            isSuccess=true;
            map.put("isSuccess",isSuccess);
            map.put("info","保存成功");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("isSuccess",isSuccess);
        map.put("info","保存失败");
        return map;
    }

    @RequestMapping("/preview")
    @ResponseBody
    public String preview(String fileName,String parentDir){
        String url=parentDir+"/"+fileName;
        try {
            String preview = bucketObjectUtil.preview(url);
            return preview;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/search")
    @ResponseBody
    public Map<String,Object> search(@RequestParam Integer uid,@RequestParam String fileName){
        Map<String,Object> map=new HashMap<>();
        List<Files> files = fileService.search(fileName, uid);
        if(files.size()>0){
            map.put("isSuccess",true);
            map.put("filelist",files);
            return map;
        }else{
            map.put("isSuccess",false);
            return map;
        }
    }

    @RequestMapping("/type")
    @ResponseBody
    public Map<String,Object> type(@RequestParam Integer uid,@RequestParam String type){
        Map<String,Object> map=new HashMap<>();
        List<Files> files = fileService.typeSearch(uid, type);
        if(files.size()>0){
            map.put("isSuccess",true);
            map.put("filelist",files);
            return map;
        }else{
            map.put("isSuccess",false);
            return map;
        }
    }
}
