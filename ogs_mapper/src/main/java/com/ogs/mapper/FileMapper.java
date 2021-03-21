package com.ogs.mapper;

import com.ogs.domain.Files;
import com.ogs.domain.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FileMapper extends Mapper<Files> {

//    @Select("select * from tb_files where uid=#{uid}")
//    List<Files> findAllFilesByUid(Integer uid);
//
//    @Select("select * from tb_files where uid=#{uid} and parent_id=#{parentId} ")
//    List<Files> findAllFilesByUidAndParentId(Integer uid,Integer parentId);


    @Insert("<script> INSERT INTO tb_files "
           + "(file_name,type,url,upload_time,uid,is_del,size,is_dir,parent_id) "
           + "VALUES "
           + " (#{file.fileName},#{file.type},#{file.url},#{file.uploadTime},#{file.uid},#{file.isDel},#{file.size},#{file.isDir},#{file.parentId}) "
           + "</script>")
   @Options(useGeneratedKeys = true, keyProperty = "fid")
   Integer insertFile(Files files);


    @Insert("<script> INSERT INTO tb_files "
            + "(file_name,type,url,upload_time,uid,is_del,size,is_dir,parent_id) "
            + "VALUES "
            + "<foreach collection = 'list' item='file' separator=',' > "
            + " (#{file.fileName},#{file.type},#{file.url},#{file.uploadTime},#{file.uid},#{file.isDel},#{file.size},#{file.isDir},#{file.parentId}) "
            + "</foreach>"
            + "</script>")
    Integer batchInsert(List<Files> list) throws Exception;


    @Select("select * from tb_files where file_name like #{fileName} and uid=#{uid} and is_dir=0")
    @Results({
            @Result(id = true,column = "fid",property = "fid"),
            @Result(column = "file_name",property = "fileName"),
            @Result(column = "type",property = "type"),
            @Result(column = "url",property = "url"),
            @Result(column = "upload_time",property = "uploadTime"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "is_del",property = "isDel"),
            @Result(column = "size",property = "size"),
            @Result(column = "is_dir",property = "isDir"),
            @Result(column = "parent_id",property = "parentId"),
    })
    List<Files> search (@Param("fileName") String fileName,@Param("uid")Integer uid);
}
