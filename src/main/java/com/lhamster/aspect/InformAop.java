package com.lhamster.aspect;

import com.lhamster.domain.BlogFriendblog;
import com.lhamster.domain.BlogInform;
import com.lhamster.domain.BlogUser;
import com.lhamster.mapper.BlogInformMapper;
import com.lhamster.mapper.BlogUserMapper;
import jdk.nashorn.internal.scripts.JO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Aspect
@Component
@Slf4j
public class InformAop {
    @Autowired
    private BlogInformMapper blogInformMapper;

    @Autowired
    private BlogUserMapper blogUserMapper;

    /**
     * 插入数据库方法
     */
    private void InsertInform(Integer userId, String content) {
        BlogInform inform = new BlogInform();
        inform.setInInformtime(new Date());
        inform.setInUserId(userId);
        inform.setInContent(content);
        inform.setInRead(false);
        // 将信息插入数据库
        blogInformMapper.insert(inform);
    }

    /**
     * 完善/修改用户信息
     *
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.UserServiceImpl.updateUser(..))")
    public void addInform(JoinPoint joinPoint) {
        BlogUser user = (BlogUser) joinPoint.getArgs()[0];
        InsertInform(user.getUId(), "恭喜您，完善/修改个人信息成功!");
    }

    /**
     * 修改密码
     *
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.UserServiceImpl.updatePwd(..))")
    public void updatePwd(JoinPoint joinPoint) {
        InsertInform(Integer.parseInt(joinPoint.getArgs()[1].toString()), "恭喜您，修改密码成功!");
    }

    /**
     * 重置密码
     *
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.UserServiceImpl.setNewPwd(..))")
    public void resetPwd(JoinPoint joinPoint) {
        String phone = joinPoint.getArgs()[0].toString();
        // 根据手机号查询用户id
        Integer userId = blogUserMapper.selectKeyByPhone(phone);
        InsertInform(userId, "恭喜您，重置密码成功!");
    }

    /**
     * 申请友链通知
     *
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.FriendBlogServiceImpl.addFriendBlog(..))")
    public void addFriendBlog(JoinPoint joinPoint) {
        Integer userId = Integer.valueOf(joinPoint.getArgs()[2].toString());
        InsertInform(userId, "恭喜您，已申请友链，正在审核中，请耐心等待...");
    }

    /**
     * 友链审核结果
     *
     * @param res
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.FriendBlogServiceImpl.checkFriendBlog(..))", returning = "res")
    public void checkFriendBlog(BlogFriendblog res) {
        InsertInform(res.getUser().getUId(), res.getFBackinfo());
    }

    /**
     * 更新友链
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.FriendBlogServiceImpl.updateFrientBlog(..))", returning = "res")
    public void updateFriendBlog(BlogFriendblog res) {
        InsertInform(res.getUser().getUId(), res.getFBackinfo());
    }

    /**
     * 添加建议
     */
    @AfterReturning(value = "execution(* com.lhamster.service.impl.AdviceServiceImpl.addAdvice(..))")
    public void addAdvice(JoinPoint joinPoint) {
        Integer userId = Integer.valueOf(joinPoint.getArgs()[2].toString());
        InsertInform(userId, "恭喜您，建议发表成功！");
    }

    /**
     * 添加bug反馈
     */
    @AfterReturning("execution(* com.lhamster.service.impl.BugServiceImpl.addBug(..))")
    public void addBug(JoinPoint joinPoint) {
        Integer userId = Integer.valueOf(joinPoint.getArgs()[1].toString());
        InsertInform(userId, "恭喜您，bug反馈添加成功!");
    }
}
