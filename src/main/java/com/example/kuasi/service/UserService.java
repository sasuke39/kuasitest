package com.example.kuasi.service;

import com.example.kuasi.dao.UserDao;
import com.example.kuasi.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    //查
    public List<User> getAllUser(){
        return userDao.findAll();
    }

    //查
    public User findOneById(int id){
        return userDao.findOne(id);
    }

    //查
    public boolean findIfExsit(String mobile){
        User oneByMobile = userDao.findOneByMobile(mobile);
        if (oneByMobile!=null&&!oneByMobile.toString().equals("")){
            return true;
        }
        return false;
    }

    //增
    public boolean CreateUser(User user){
        if (user==null) return false;
        if (userDao.findOneByMobile(user.getMobile())!=null){
            return userDao.newUser(user);
        }else{
            return false;
        }

    }

    //改
    public boolean updateUser(User user){
        return userDao.updateUser(user);
    }

    //删
    public boolean ReallyDeleteUser(int id){
        return userDao.deleteUser(id);
    }

    public boolean deleteState(int id,String state){
        return userDao.setState(id,state);
    }



}
