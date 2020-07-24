package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogBook;
import com.lhamster.domain.BlogBug;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.BugService;
import com.lhamster.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
@RestController
public class BugController {
    @Autowired
    private Audience audience;

    @Autowired
    private BugService bugService;

    /**
     * bug列表
     *
     * @param vo not null
     * @param my myself
     * @return
     */
    @GetMapping("/bug")
    public Result<List<BlogBug>> bugs(QueryVo vo, @RequestParam(value = "my", defaultValue = "false") Boolean my, HttpServletRequest request) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        Integer userId = null;
        if (my) {
            // 获取用户id
            userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        }
        return bugService.bugs(vo, userId);
    }

    /**
     * 添加bug反馈
     *
     * @param content 反馈内容
     * @return
     */
    @PostMapping("/bug")
    public Result addBug(String content, HttpServletRequest request) {
        if (StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户id
        Integer userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        try {
            /*添加bug反馈*/
            bugService.addBug(content, userId);
            return new Result(ResultCode.BUG_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BUG_ADD_FAILED);
        }
    }

    /**
     * 删除bug反馈
     *
     * @param id 不能为空
     * @return
     */
    @DeleteMapping("/bug")
    public Result deleteBug(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            /*删除bug反馈*/
            bugService.deleteBug(id);
            return new Result(ResultCode.BUG_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.BUG_DELETE_FAILED);
        }
    }
}
