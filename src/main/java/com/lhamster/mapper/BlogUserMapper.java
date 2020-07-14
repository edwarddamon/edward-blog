package com.lhamster.mapper;

import com.lhamster.domain.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogUserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(BlogUser record);

    BlogUser selectByPrimaryKey(Integer uId);

    List<BlogUser> selectAll();

    int updateByPrimaryKey(BlogUser record);

    /*根据手机号查询用户*/
    BlogUser selectByPhone(String phone);

    /*登录*/
    BlogUser login(@Param("userPhone") String userPhone, @Param("password") String password, @Param("type") String type);

    /*修改密码*/
    void updatePwd(@Param("parseInt") int parseInt, @Param("newPwd") String newPwd);

    /*重置密码*/
    void resetPwd(@Param("phone") String phone, @Param("password") String password);

    /*根据手机查询id*/
    Integer selectKeyByPhone(String phone);
}