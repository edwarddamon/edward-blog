package com.lhamster.controller;

import com.lhamster.domain.BlogDiary;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.DiaryService;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/22
 */
@Slf4j
@RestController
public class DiaryController {
    /*图片格式*/
    private static final List<String> suffix = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif"));

    @Autowired
    private DiaryService diaryService;

    /**
     * 日记列表
     *
     * @return
     */
    @GetMapping("/diaries")
    public Result<List<BlogDiary>> diaries() {
        return new Result<>(ResultCode.SUCCESS, diaryService.diaries());
    }

    /**
     * 上传日记图片
     *
     * @param file
     * @param session
     * @return
     */
    @PostMapping("/diary_pic")
    public Result diary_pic(@RequestParam("file") MultipartFile file, HttpSession session) {
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
            String articlePicUrl = TencentCOSUtil.uploadObject(localFile, "diaryPicture/" + filename);
            return new Result<>(ResultCode.DIARY_UPLOAD_SUCCESS, articlePicUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.DIARY_UPLOAD_FAILED);
        }
    }

    /**
     * 添加日记
     *
     * @param content 不能为空
     * @param picUrl
     * @return
     */
    @PostMapping("/diary")
    public Result addDiary(String content, String picUrl) {
        if (StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 添加日记
            diaryService.addDiiary(content, picUrl);
            return new Result(ResultCode.DIARY_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.DIARY_ADD_FAILED);
        }
    }

    /**
     * 修改日记
     *
     * @param id        不能为空
     * @param content
     * @param newPicUrl
     * @return
     */
    @PutMapping("/diary")
    public Result updateDiary(Integer id, String content, String newPicUrl) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 修改日记
            diaryService.updateDiary(id, content, newPicUrl);
            return new Result(ResultCode.DIARY_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.DIARY_UPDATE_FAILED);
        }
    }

    /**
     * 删除日记
     *
     * @param id 不能为空
     * @return
     */
    @DeleteMapping("/diary")
    public Result deleteDiary(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除日记
            diaryService.deleteDiary(id);
            return new Result(ResultCode.DIARY_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.DIARY_DELETE_FAILED);
        }
    }
}
