package com.lhamster.controller;

import com.lhamster.domain.BlogTag;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.TagService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
@Slf4j
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 标签列表
     *
     * @return
     */
    @GetMapping("/tags")
    public Result<List<BlogTag>> tags() {
        return new Result<>(ResultCode.SUCCESS, tagService.tags());
    }

    /**
     * 查询某个标签
     *
     * @param id
     * @return
     */
    @GetMapping("/tag")
    public Result<BlogTag> tag(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return new Result<>(ResultCode.SUCCESS, tagService.tag(id));
    }

    /**
     * 添加标签
     *
     * @param content 不能为空
     * @return
     */
    @PostMapping("/tag")
    public Result addTag(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 添加标签
            tagService.addTag(content);
            return new Result(ResultCode.TAG_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.TAG_ADD_FAILED);
        }
    }

    /**
     * 更新标签内容
     *
     * @param id
     * @param content
     * @return
     */
    @PutMapping("/tag")
    public Result updateTag(Integer id, String content) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 更新标签
            tagService.updateTag(id, content);
            return new Result(ResultCode.TAG_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.TAG_UPDATE_FAILED);
        }
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tag")
    public Result deleteTag(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除标签
            tagService.deleteTag(id);
            return new Result(ResultCode.TAG_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.TAG_DELETE_FAILED);
        }
    }
}
