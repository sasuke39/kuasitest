package com.example.kuasi.controller;

import com.example.kuasi.access.AccessLimit;
import com.example.kuasi.domain.User;
import com.example.kuasi.result.Result;
import com.example.kuasi.service.UserService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;




public class UserController {
    @Autowired
    UserService userService;


    @AccessLimit(minute = 30,maxCount = 5)
    @RequestMapping(value = "newUser",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> newUser(User user, HttpServletRequest request){
        Result<String> result = new Result<>();
        //无重复 不空
//        HttpSession session = request.getSession();
//        session.get

        if (user.getMobile()==null||user.getPassword()==null
                || user.getMobile().replaceAll(" ","").equals("")
                ||user.getPassword().replaceAll(" ","").equals("")){
            result.setCode(400);
            result.setMessage("注册失败");
            result.setT("格式错误！");
            return result;
        }
        if (user.getMobile().length()!=11){
            result.setCode(502);
            result.setMessage("注册失败");
            result.setT("手机号格式不对！");
            return result;
        }
        if (userService.findIfExsit(user.getMobile())){
            result.setCode(501);
            result.setMessage("注册失败");
            result.setT("该手机已注册！");
            return result;
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        if (userService.CreateUser(user)){
            result.setCode(200);
            result.setMessage("注册成功");
        }else {
            result.setCode(500);
            result.setMessage("注册失败");
        }
        return result;
    }


    @RequestMapping("updateUser")
    @ResponseBody
    public Result<String> updateUser(User user){
        Result<String> result = new Result<>();
        user.setUpdateTime(new Date());
        if (userService.updateUser(user)){
            result.setCode(200);
            result.setMessage("修改成功");
        }else {
            result.setCode(500);
            result.setMessage("修改失败");
        }
        return result;
    }

    @RequestMapping("findAllUser")
    @ResponseBody
    public Result<List> getAllUser(){
        Result<List> result = new Result<>();
        result.setCode(500);
        result.setMessage("获取失败！");

        List<User> allUser = userService.getAllUser();
        if (allUser!=null&&!allUser.toString().equals("[]")){
            result.setCode(200);
            result.setMessage("获取成功！");
            result.setT(allUser);
        }

        return result;
    }

    @RequestMapping("findOneById")
    @ResponseBody
    public Result<User> getOne(int id){
        Result<User> result = new Result<>();
        User oneById = userService.findOneById(id);
        if (oneById !=null){
            result.setCode(200);
            result.setMessage("获取成功!");
            result.setT(oneById);
        }else {
            result.setCode(500);
            result.setMessage("获取失败!");
        }
        return result;
    }

    @RequestMapping("setDeleteState")
    @ResponseBody
    public Result setState(User user){
        Result result = new Result();
        user.setUpdateTime(new Date());
        if (userService.deleteState(user.getId(),"-1")){
            result.setCode(200);
            result.setMessage("设置成功");
        }else {
            result.setCode(500);
            result.setMessage("设置失败");
        }
        return result;
    }


}
