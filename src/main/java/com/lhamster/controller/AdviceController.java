package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogAdvice;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.AdviceService;
import com.lhamster.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
public class AdviceController {
    @Autowired
    private Audience audience;

    @Autowired
    private AdviceService adviceService;

    /**
     * 建议列表
     *
     * @param vo
     * @param my 若要查询当前登录用户的建议，传true，否则不传
     * @return
     */
    @GetMapping("/advice")
    public Result<List<BlogAdvice>> advice(QueryVo vo, Boolean my, HttpServletRequest request) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        Integer userId = null;
        if (my) {
            // 获取用户id
            userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        }
        return adviceService.advice(vo, userId);
    }

    /**
     * 添加建议
     *
     * @param title
     * @param content
     * @return
     */
    @PostMapping("/advice")
    public Result addAdvice(String title, String content, HttpServletRequest request) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户id
        Integer userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        try {
            // 添加建议
            adviceService.addAdvice(title, content, userId);
            return new Result(ResultCode.AFVICE_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.AFVICE_ADD_FAILED);
        }
    }

    /**
     * 删除功能建议
     *
     * @param id 不能为空
     * @return
     */
    @DeleteMapping("/advice")
    public Result deleteAdvice(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除建议
            adviceService.deleteAdvice(id);
            return new Result(ResultCode.AFVICE_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.AFVICE_DELETE_FAILED);
        }
    }
}
