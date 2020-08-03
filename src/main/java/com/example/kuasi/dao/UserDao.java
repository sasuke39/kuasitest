package com.example.kuasi.dao;

import com.example.kuasi.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("select * from user")
    public List<User> findAll();

    @Select("select * from user where id = #{id}")
    public User findOne(int id);

    @Select("select * from user where mobile = #{mobile}")
    public User findOneByMobile(String  mobile);

    @Delete("delete from user where id =#{id}")
    public boolean deleteUser(int id);

    @Update("update user set mobile=#{mobile},password=#{password},sex=#{sex},birthdate=#{birthdate},address=#{address},email=#{email},state=#{state},create_time=#{create_time},update_time=#{update_time} where id = #{id}")
    public boolean updateUser(User user);

    @Insert("insert into user(mobile,password,sex,birthdate,address,email,state,create_time,update_time) values(#{mobile}, #{password},#{sex},#{birthdate},#{address},#{email}),#{state}),#{create_time},#{update_time}) ")
    public boolean newUser(User user);

    @Update("update user set state = #{state} where id =#id")
    public boolean setState(int id,String state);

}
