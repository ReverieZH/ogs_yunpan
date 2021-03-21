package com.ogs.service;

import com.ogs.utils.DateUtil;
import com.ogs.utils.Rename_String;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;

public class UtilTest {
    @Test
    public void testString(){
        String s="rerefev";
        System.out.println(s.endsWith("/"));

    }

    @Test
    public void testSubpPefixString(){

        System.out.println(Rename_String.subPrefix("ogs21010901/reverie/11/"));
    }

    @Test
    public void getUrltest(){
        String url = Rename_String.getUrl("http://localhost:8080/files/createshare");
        System.out.println(url);
    }

    @Test
    public void dateTest() throws ParseException {
        System.out.println(DateUtil.getCurrentTime());
        java.util.Date sqlDate = DateUtil.getSqlDate();
        System.out.println(sqlDate);
    }

}
