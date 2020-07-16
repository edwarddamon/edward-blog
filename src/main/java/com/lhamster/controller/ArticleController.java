package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.ArticleService;
import com.lhamster.util.JwtTokenUtil;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Slf4j
@RestController
public class ArticleController {
    /*图片格式*/
    private static final List<String> suffix = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    @Autowired
    private Audience audience;

    @Autowired
    private ArticleService articleService;

    /**
     * 上传文章图片
     *
     * @param file
     * @return
     */
    @PostMapping("/article_pic")
    public Result article_pic(@RequestParam("file") MultipartFile file, HttpSession session) {
        if (file.isEmpty()) {
            throw new ResultException(ResultCode.ARTICLE_UPLOAD_FAILED);
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
        try {
            // 将MultipartFile转为File从内存中写入磁盘
            file.transferTo(localFile);
            // 新头像的地址
            String articlePicUrl = TencentCOSUtil.uploadObject(localFile, "articlePicture/" + filename);
            return new Result<String>(ResultCode.ARTICLE_UPLOAD_SUCCESS, articlePicUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.ARTICLE_UPLOAD_FAILED);
        }
    }

    /**
     * 发布/新增文章
     *
     * @param title
     * @param content
     * @param categoryId
     * @return
     */
    @PostMapping("/article")
    public Result article(String title, String content, Integer categoryId, HttpServletRequest request) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(categoryId)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info("标题===" + title);
        log.info("内容===" + content);
        log.info("分类id===" + categoryId);
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            BlogArticle article = new BlogArticle();
            article.setATitle(title);
            article.setAContent(content);
            article.setACreatetime(new Date());
            article.setALikecount(0);
            article.setAVisitcount(0);
            article.setAStatus(true);
            article.setACateId(categoryId);
            article.setAUserId(Integer.parseInt(userId));
            // 插入到数据库
            articleService.insertArticle(article);
            return new Result(ResultCode.ARTICLE_PUBLISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.ARTICLE_PUBLISH_FAILED);
        }
    }
}
