package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.UserService;
import com.lhamster.util.JwtTokenUtil;
import com.lhamster.util.SmsUtils;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Slf4j
@RestController
public class UserController {
    /*图片格式*/
    private static final List<String> suffix = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    @Autowired
    private UserService userService;

    @Autowired
    private Audience audience;

    /*注入redisTemplate*/
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信
     */
    private Result sendMessage(String phone, Integer type) {
        String randomCode = SmsUtils.generateRandomCode();// 六位随机数验证码
        long currTime = new Date().getTime();// 当前时间的时间戳
        String res = SmsUtils.sendSms(randomCode, new String[]{"86" + phone}, type);
        if ("success".equals(res)) {// 发送成功
            // 以手机号为key，将随机数验证码和当前时间的时间戳存入redis中
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", randomCode);
            map.put("time", currTime);
            map.put("type", type);
            redisTemplate.opsForHash().putAll(phone, map);
            log.info(phone + "   " + randomCode + "   " + currTime);
            return new Result(ResultCode.SMS_SEND_SUCCESS);
        } else {// 发送失败
            return new Result(ResultCode.SMS_SEND_FAILED);
        }
    }

    /**
     * 短信验证
     *
     * @param "手机号"
     * @return Result
     */
    @PostMapping("/register/{phone}/{type}")
    public Result messageCheck(@PathVariable("phone") String phone, @PathVariable("type") String type) {
        log.info("手机号======" + phone + "\ttype===" + type);
        if ("0".equals(type)) { // 注册
            try {
                // 检查手机号是否已经存在
                Boolean result = userService.checkPhone(phone);
                if (result) {// 该手机号已存在
                    return new Result(ResultCode.USER_REGISTER_EXISTED);
                } else {// 该手机号不存在,发送短信验证
                    return sendMessage(phone, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResultException(ResultCode.SYSTEM_UNKNOWN_CHECKED);
            }
        } else if ("1".equals(type)) { // 密码重置
            return sendMessage(phone, 1);
        } else {
            throw new ResultException(ResultCode.SYSTEM_UNKNOWN_CHECKED);
        }
    }

    /**
     * 注册
     *
     * @param blogUser
     * @return
     */
    @PostMapping("/register")
    public Result register(BlogUser blogUser, String code) {
        log.info("requestInfo====" + blogUser.toString());
        log.info("code===" + code);
        // 去redis缓存检查code是否存在，对比是否过期
        String redisCode = (String) redisTemplate.opsForHash().get(blogUser.getUPhone(), "code");
        long redisTime = (long) redisTemplate.opsForHash().get(blogUser.getUPhone(), "time");
        if (!StringUtils.isEmpty(redisCode) && !StringUtils.isEmpty(redisTime)) { // 读取到了存入redis的该手机号的信息
            // 判断该信息是否过期
            long nowTime = new Date().getTime();
            if (nowTime - redisTime < 120 * 1000) { // 没过期：对比redisCode和前端传递的code是否一致
                if (redisCode.equals(code)) {// 一致
                    //将手机号和密码存入数据库，注册成功
                    try {
                        userService.register(blogUser);
                        // 删除redis中的相关信息
                        redisTemplate.delete(blogUser.getUPhone());
                        return new Result(ResultCode.USER_REGISTER_SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ResultException(ResultCode.FAIL);
                    }
                } else { // 不一致
                    return new Result(ResultCode.SMS_GET_WRONG);
                }
            } else { // 过期
                redisTemplate.delete(blogUser.getUPhone());
                throw new ResultException(ResultCode.SMS_GET_PASSED);
            }
        } else {
            throw new ResultException(ResultCode.SMS_GET_FAILED);
        }
    }

    /**
     * 通过手机验证码重新设置密码
     *
     * @param phone    手机号
     * @param password 新密码
     * @param code     手机验证码
     * @return
     */
    @PutMapping("/register")
    public Result findPwd(String phone, String password, String code) {
        log.info(phone + "\t" + password + "\t" + code);
        // 去redis缓存检查code是否存在，对比是否过期
        String redisCode = (String) redisTemplate.opsForHash().get(phone, "code");
        long redisTime = (long) redisTemplate.opsForHash().get(phone, "time");
        if (!StringUtils.isEmpty(redisCode) && !StringUtils.isEmpty(redisTime)) { // 读取到了存入redis的该手机号的信息
            // 判断该信息是否过期
            long nowTime = new Date().getTime();
            if (nowTime - redisTime < 120 * 1000) { // 没过期：对比redisCode和前端传递的code是否一致
                if (redisCode.equals(code)) {// 一致
                    // 将新密码存入数据库
                    try {
                        userService.setNewPwd(phone, password);
                        // 删除redis中的相关信息
                        redisTemplate.delete(phone);
                        return new Result(ResultCode.USER_PWD_RESET_SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ResultException(ResultCode.FAIL);
                    }
                } else { // 不一致
                    return new Result(ResultCode.SMS_GET_WRONG);
                }
            } else { // 过期
                redisTemplate.delete(phone);
                throw new ResultException(ResultCode.SMS_GET_PASSED);
            }
        } else {
            throw new ResultException(ResultCode.SMS_GET_FAILED);
        }
    }

    /**
     * 登录
     *
     * @param userPhone "手机号"
     * @param password  "密码"
     * @return
     */
    @PostMapping("/login")
    public Result login(String userPhone, String password, HttpServletRequest request, HttpServletResponse response) {
        String type = request.getHeader("type");
        log.info("登录信息===手机号：" + userPhone + "\t密码：" + password + "\ttype：" + type);
        if (!StringUtils.isEmpty(type)) {
            try {
                BlogUser user = userService.login(userPhone, password, type);
                if (!StringUtils.isEmpty(user)) { // 登录成功
                    // 生成token
                    String token = JwtTokenUtil.createJWT(user.getUId().toString(), user.getUNickname(), "user", audience);
                    log.info(user.toString());
                    log.info("登陆成功===" + token);
                    // 将token存入响应头返回给前端
                    response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
                    return new Result(ResultCode.USER_LOGIN_SUCCESS);
                } else { // 登录失败
                    return new Result(ResultCode.USER_LOGIN_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResultException(ResultCode.FAIL);
            }
        } else {
            throw new ResultException(ResultCode.PERMISSION_ORIGINAL_ERROR);
        }
    }

    /**
     * 修改个人信息
     *
     * @param blogUser "昵称"、"性别"、"生日"、"邮箱"
     * @return
     */
    @PutMapping("/user")
    public Result updateUser(BlogUser blogUser, HttpServletRequest request) {
        log.info("info===" + blogUser);
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        if (!StringUtils.isEmpty(userId)) {
            log.info("用户id===" + userId);
            try {
                // 修改用户信息
                blogUser.setUId(Integer.valueOf(userId));
                userService.updateUser(blogUser);
                return new Result(ResultCode.USER_UPDATE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResultException(ResultCode.USER_UPDATE_FAILED);
            }
        } else {
            throw new ResultException(ResultCode.PERMISSION_TOKEN_INVALID);
        }
    }

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    @PatchMapping("/user")
    public Result updatePwd(String oldPwd, String newPwd, HttpServletRequest request) {
        log.info("旧密码===" + oldPwd + "\t新密码===" + newPwd);
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        if (!StringUtils.isEmpty(userId)) {
            log.info("用户id===" + userId);
            try {
                // 检查旧密码是否正确
                Boolean res = userService.checkOldPwd(oldPwd, userId);
                if (res) { // 正确
                    // 修改密码
                    userService.updatePwd(newPwd, userId);
                    return new Result(ResultCode.USER_PWD_SUCCESS);
                } else { // 错误
                    return new Result(ResultCode.USER_PWD_WRONG);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResultException(ResultCode.USER_PWD_FAILED);
            }
        } else {
            throw new ResultException(ResultCode.PERMISSION_TOKEN_INVALID);
        }
    }

    /**
     * 更换系统默认头像
     *
     * @param newHeadPicUrl
     * @return
     */
    @PostMapping("/user")
    public Result updateHeadPic(String newHeadPicUrl, HttpServletRequest request) {
        if (StringUtils.isEmpty(newHeadPicUrl)) {
            throw new ResultException(ResultCode.USER_HEADPIC_EMPTY);
        }
        log.info(newHeadPicUrl);
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 查询出对应的旧头像地址
            BlogUser blogUser = userService.queryById(Integer.parseInt(userId));
            String oldHeadPicUrl = blogUser.getUHeadpicture();
            String oldHeadPicName = oldHeadPicUrl.substring(oldHeadPicUrl.lastIndexOf("/"));
            if (oldHeadPicName.length() > 6) {// 如果旧头像地址不是系统默认头像地址，删除旧头像
                TencentCOSUtil.deletefile("headPicture/" + oldHeadPicName);
            }
            // 将新头像插入数据库
            userService.updateHeadPic(Integer.parseInt(userId), newHeadPicUrl);
            return new Result(ResultCode.USER_HEADPIC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.USER_HEADPIC_FAIL);
        }
    }

    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    @PostMapping("/upLoadFile")
    public Result upLoadHeadPic(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest request) {
        if (file.isEmpty()) {
            throw new ResultException(ResultCode.USER_HEADPIC_FAILED);
        }
        String filename = file.getOriginalFilename();
        log.info("文件名:" + filename);
        // uuid生成随机的文件名
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        // 新的文件名+文件后缀
        String fileSuffix = filename.substring(filename.lastIndexOf("."));
        if (!suffix.contains(fileSuffix)) { // 不是指定格式
            throw new ResultException(ResultCode.USER_HEADPIC_TYPE_ERROR);
        }
        filename = uuid + fileSuffix;
        // 即将写入磁盘的地址
        File localFile = new File(session.getServletContext().getRealPath("/") + filename);
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 将MultipartFile转为File从内存中写入磁盘
            file.transferTo(localFile);
            // 新头像的地址
            String headPicUrl = TencentCOSUtil.uploadObject(localFile, "headPicture/" + filename);
            // 查询旧头像地址
            BlogUser blogUser = userService.queryById(Integer.parseInt(userId));
            // 删除旧头像
            String oldHeadPicUrl = blogUser.getUHeadpicture();
            String oldFileName = oldHeadPicUrl.substring(oldHeadPicUrl.lastIndexOf("/") + 1);
            if (oldFileName.length() > 6) { // 默认的图标不删除
                TencentCOSUtil.deletefile("headPicture/" + oldFileName);
            }
            // 将新头像地址插入数据库
            userService.updateHeadPic(blogUser.getUId(), headPicUrl);
            return new Result(ResultCode.USER_HEADPIC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.USER_HEADPIC_FAILED);
        }
    }

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("/user-back")
    public Result<List<BlogUser>> users(QueryVo vo) {
        return userService.queryAll(vo);
    }

    /**
     * 添加用户
     *
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/user-back")
    public Result user(String phone, String password) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new ResultException(ResultCode.USER_ADD_EMPTY);
        }
        // 检查手机号是否存在
        Boolean result = userService.checkPhone(phone);
        // 添加用户
        if (result) {
            // 存在
            return new Result(ResultCode.USER_REGISTER_EXISTED);
        }
        try {
            // 不存在
            BlogUser blogUser = new BlogUser();
            blogUser.setUPhone(phone);
            blogUser.setUPassword(password);
            userService.register(blogUser);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }

    /**
     * 修改用户信息
     *
     * @param blogUser
     * @return
     */
    @PutMapping("/user-back")
    public Result updateUserItem(BlogUser blogUser) {
        if (StringUtils.isEmpty(blogUser.getUId())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info(blogUser.toString());
        try {
            // 修改用户信息
            userService.updateUser(blogUser);
            return new Result(ResultCode.USER_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }

    /**
     * 设置/取消为管理员
     *
     * @param id
     * @param decide
     * @return
     */
    @PatchMapping("/user-back/{id}/{decide}")
    public Result setAdmin(@PathVariable("id") Integer id, @PathVariable("decide") Integer decide) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(decide)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info(id + "\t" + decide);
        try {
            // 设置取消管理员
            userService.setAdmin(id, decide);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }
}
