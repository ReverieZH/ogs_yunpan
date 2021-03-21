package com.ogs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ServiceMainController {

    @RequestMapping("servicemain")
    public String servicemain(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        return "service_main";
    }

    @RequestMapping("/datamain")
    public String serviceData(){
        return "/data_departmentUI.jsp";
    }

}
