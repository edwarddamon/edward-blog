package com.lhamster.mapper;

import com.lhamster.domain.BlogUser;
import org.apache.ibatis.annotations.Mapper;

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
}