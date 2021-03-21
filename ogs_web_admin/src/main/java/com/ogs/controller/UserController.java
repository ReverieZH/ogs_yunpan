package com.ogs.controller;

import com.ogs.domain.Users;
import com.ogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过用户姓名模糊查询用户
     * @param name
     * @return
     */
    @RequestMapping("/findUserByName")
    public ResponseEntity<List<Users>> findUserByName(@RequestParam("name")String name){
        List<Users> users = userService.findUserByName(name);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping("/login_page")
    public String login_page() {
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, Users user, HttpSession session) {
        boolean issuccess=false;
        Map<String,Object> map=new HashMap<>();
        user = userService.login(user);
        if(user!= null){
                issuccess=true;
                request.getSession().setAttribute("uid",user.getUid());
                System.out.println("========login=========="+user.getJobNumber());
                request.getSession().setAttribute("jobNumber",user.getJobNumber());
                request.getSession().setAttribute("fid",2);
                map.put("isSuccess",issuccess);
                map.put("info","成功");
                return map;
        }else {
                issuccess=false;
                map.put("isSuccess",issuccess);
                map.put("info","未找到用户");
                return map;
        }
    }

}
