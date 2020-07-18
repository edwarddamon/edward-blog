package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.ArticleService;
import com.lhamster.util.JwtTokenUtil;
import com.lhamster.util.TencentCOSUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
     * 删除博客文章图片
     *
     * @param picUrl
     * @return
     */
    @DeleteMapping("/article_pic")
    public Result deleteArticle_pic(String picUrl) {
        if (StringUtils.isEmpty(picUrl)) {
            throw new ResultException(ResultCode.ARTICLE_UPLOAD_FAILED);
        }
        log.info(picUrl);
        picUrl = picUrl.substring(picUrl.lastIndexOf("/"));
        log.info(picUrl);
        try {
            // 删除腾讯云博客图片
            TencentCOSUtil.deletefile("articlePicture" + picUrl);
            return new Result(ResultCode.ARTICLE_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.ARTICLE_DELETE_FAILED);
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
    public Result article(String title, String content, Integer categoryId, @RequestParam("pictures[]") String pictures, HttpServletRequest request) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(categoryId) || StringUtils.isEmpty(pictures)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info("标题===" + title);
        log.info("内容===" + content);
        log.info("分类id===" + categoryId);
        log.info("图片地址===" + pictures);
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
            article.setAPicture(pictures);
            article.setAUserId(Integer.parseInt(userId));
            // 插入到数据库
            articleService.insertArticle(article);
            return new Result(ResultCode.ARTICLE_PUBLISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.ARTICLE_PUBLISH_FAILED);
        }
    }

    /**
     * 根据博客文章id查询博客
     *
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result<BlogArticle> queryArticle(@PathVariable("id") Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 博客访问次数增加
            articleService.addBlogVistCount(id);
            return new Result<>(ResultCode.SUCCESS, articleService.queryArticle(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.VISIT_COUNT_FAILED);
        }
    }

    /**
     * 博客列表
     *
     * @param vo
     * @return
     */
    @GetMapping("/article")
    public Result<List<BlogArticle>> queryMoreArticle(QueryVo vo, HttpServletRequest request) {
        if (!StringUtils.isEmpty(vo.getJudge())) {
            // 获取用户id
            String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
            vo.setUserId(Integer.valueOf(userId));
        }
        return articleService.queryMoreArticle(vo);
    }

    /**
     * 修改博客
     *
     * @param id
     * @param title
     * @param content
     * @return
     */
    @PutMapping("/article")
    public Result updateArticle(Integer id, String title, String content, @RequestParam("pictures[]") String pictures) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(pictures)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info("id===" + id);
        log.info("标题===" + title);
        log.info("内容===" + content);
        log.info("图片地址===" + pictures);
        try {
            articleService.updateArticle(id, title, content, pictures);
            return new Result(ResultCode.ARTICLE_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.ARTICLE_UPDATE_FAILED);
        }
    }

    /**
     * 修改博客文章状态
     *
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/article")
    public Result fakeDeleteArticle(Integer id, Boolean status) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(status)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        log.info(id.toString());
        log.info(status.toString());
        try {
            // 标记文章状态
            articleService.deleteArticle(id, status);
            if (status) {
                // 恢复
                return new Result(ResultCode.ARTICLE_RECOVER_SUCCESS);
            } else {
                // 删除
                return new Result(ResultCode.BLOGARTICLE_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (status) {
                // 恢复
                return new Result(ResultCode.ARTICLE_RECOVER_FAILED);
            } else {
                // 删除
                return new Result(ResultCode.BLOGARTICLE_DELETE_FAILED);
            }
        }
    }

    /**
     * 删除博客文章
     *
     * @param articleId
     * @return
     */
    @DeleteMapping("/article")
    public Result deleteArticle(Integer articleId) {
        if (StringUtils.isEmpty(articleId)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 彻底删除博客
            articleService.delete(articleId);
            return new Result(ResultCode.BLOGARTICLE_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BLOGARTICLE_DELETE_FAILED);
        }
    }

    /**
     * 点赞/取消点赞
     *
     * @param id
     * @param like
     * @return
     */
    @PutMapping("/article-like")
    public Result setLikeCount(Integer id, Boolean like) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(like)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 点赞
            articleService.setLike(id, like);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }
}
